package com.ssafy.onda.api.diary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.onda.api.diary.dto.MemoListDto;
import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.api.diary.repository.BackgroundRepository;
import com.ssafy.onda.api.member.entity.Member;
import com.ssafy.onda.api.member.entity.MemberMemo;
import com.ssafy.onda.api.member.repository.MemberMemoRepository;
import com.ssafy.onda.api.member.repository.MemberRepository;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.*;
import com.ssafy.onda.global.common.entity.*;
import com.ssafy.onda.global.common.repository.*;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {

    private final BackgroundRepository backgroundRepository;

    private final MemberRepository memberRepository;

    private final MemberMemoRepository memberMemoRepository;

    private final TextRepository textRepository;

    private final AccountBookRepository accountBookRepository;

    private final AccountBookItemRepository accountBookItemRepository;

    private final ChecklistRepository checklistRepository;

    private final ChecklistItemRepository checklistItemRepository;

    private final MemoTypeRepository memoTypeRepository;

    @Transactional
    @Override
    public void save(CustomUserDetails details, ReqDiaryDto reqDiaryDto) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        // 날짜 확인
        String diaryDate = reqDiaryDto.getDiaryDate();
        checkDateValidation(diaryDate);

        ObjectMapper mapper = new ObjectMapper();
        List<Text> texts = new ArrayList<>();
        List<AccountBook> accountBooks = new ArrayList<>();
        List<Checklist> checklists = new ArrayList<>();

        Map<AccountBook, List<AccountBookItemDto>> accountBookMap = new HashMap<>();
        Map<Checklist, List<ChecklistItemDto>> checklistMap = new HashMap<>();

        // 입력을 떡메 단위로 분류
        List<MemoListDto> memoListDtos = reqDiaryDto.getMemoList();
        for (MemoListDto memoListDto : memoListDtos) {
            if (memoListDto.getMemoTypeSeq() == 1) {
                TextDto textDto = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                texts.add(Text.builder()
                        .x(memoListDto.getX())
                        .y(memoListDto.getY())
                        .width(memoListDto.getWidth())
                        .height(memoListDto.getHeight())
                        .textHeader(textDto.getTextHeader())
                        .textContent(textDto.getTextContent())
                        .build());
            } else if (memoListDto.getMemoTypeSeq() == 2) {
                AccountBookDto accountBookDto = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                AccountBook accountBook = AccountBook.builder()
                        .x(memoListDto.getX())
                        .y(memoListDto.getY())
                        .width(memoListDto.getWidth())
                        .height(memoListDto.getHeight())
                        .totalAmount(accountBookDto.getTotalAmount())
                        .totalDeposit(accountBookDto.getTotalDeposit())
                        .totalWithdraw(accountBookDto.getTotalWithdraw())
                        .build();
                accountBooks.add(accountBook);
                accountBookMap.put(accountBook, accountBookDto.getAccountBookItems());
            } else if (memoListDto.getMemoTypeSeq() == 3) {
                ChecklistDto checklistDto = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                Checklist checklist = Checklist.builder()
                        .x(memoListDto.getX())
                        .y(memoListDto.getY())
                        .width(memoListDto.getWidth())
                        .height(memoListDto.getHeight())
                        .checklistHeader(checklistDto.getChecklistHeader())
                        .build();
                checklists.add(checklist);
                checklistMap.put(checklist, checklistDto.getChecklistItems());
            } else {
                throw new CustomException(LogUtil.getElement(), INVALID_MEMO_TYPE);
            }
        }

        // 떡메 저장
        List<Text> savedTexts = textRepository.saveAll(texts);

        List<AccountBook> savedAccountBooks = accountBookRepository.saveAll(accountBooks);
        // 떡메 항목 저장
        for (AccountBook savedAccountBook : savedAccountBooks) {
            List<AccountBookItemDto> accountBookItemDtos = accountBookMap.get(savedAccountBook);
            List<AccountBookItem> accountBookItems = new ArrayList<>();
            for (AccountBookItemDto accountBookItemDto : accountBookItemDtos) {
                accountBookItems.add(AccountBookItem.builder()
                        .description(accountBookItemDto.getDescription())
                        .deposit(accountBookItemDto.getDeposit())
                        .withdraw(accountBookItemDto.getWithdraw())
                        .accountBook(savedAccountBook)
                        .build());
            }
            accountBookItemRepository.saveAll(accountBookItems);
        }

        List<Checklist> savedChecklists = checklistRepository.saveAll(checklists);
        for (Checklist savedChecklist : savedChecklists) {
            List<ChecklistItemDto> checklistItemDtos = checklistMap.get(savedChecklist);
            List<ChecklistItem> checklistItems = new ArrayList<>();
            for (ChecklistItemDto checklistItemDto : checklistItemDtos) {
                checklistItems.add(ChecklistItem.builder()
                        .isChecked(checklistItemDto.getIsChecked())
                        .checklistItemText(checklistItemDto.getChecklistItemText())
                        .checklist(savedChecklist)
                        .build());
            }
            checklistItemRepository.saveAll(checklistItems);
        }

        // 회원떡메(memberMemo), 회원떡메가 가지고 있는 떡메모지 삭제
        // delete method 구현 이후에 추가

        // background 생성
        Background savedBackground = backgroundRepository.save(Background.builder()
                .diaryDate(LocalDate.parse(diaryDate))
                .member(member)
                .build());

        // member과 background로 MemberMemo 만들기
        List<MemberMemo> memberMemos = new ArrayList<>();
        for (Text savedText : savedTexts) {
            memberMemos.add(MemberMemo.builder()
                    .memoSeq(savedText.getTextSeq())
                    .memoType(memoTypeRepository.findByMemoTypeSeq(1L))
                    .background(savedBackground)
                    .build());
        }

        for (AccountBook savedAccountBook : savedAccountBooks) {
            memberMemos.add(MemberMemo.builder()
                    .memoSeq(savedAccountBook.getAccountBookSeq())
                    .memoType(memoTypeRepository.findByMemoTypeSeq(2L))
                    .background(savedBackground)
                    .build());
        }

        for (Checklist savedChecklist : savedChecklists) {
            memberMemos.add(MemberMemo.builder()
                    .memoSeq(savedChecklist.getChecklistSeq())
                    .memoType(memoTypeRepository.findByMemoTypeSeq(3L))
                    .background(savedBackground)
                    .build());
        }

        // member memo 저장
        memberMemoRepository.saveAll(memberMemos);
    }

    private void checkDateValidation(String date) {

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            throw new CustomException(LogUtil.getElement(), INVALID_DATE_FORMAT);
        }
    }
}
