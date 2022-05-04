package com.ssafy.onda.api.diary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.onda.api.diary.dto.FindMemosDto;
import com.ssafy.onda.api.diary.dto.MemoListDto;
import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.dto.response.ResDiaryDto;
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
import java.util.*;

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

        if (memoListDtos.size() == 0) {
            throw new CustomException(LogUtil.getElement(), NO_MEMO_AVAILABLE);
        }

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
        Optional<Background> optionalBackground = backgroundRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate));
        optionalBackground.ifPresent(this::delete);
        Background savedBackground = optionalBackground.orElseGet(() -> backgroundRepository.save(Background.builder()
                .diaryDate(LocalDate.parse(diaryDate))
                .member(member)
                .build())
        );

        MemoType memoTypeSeq = memoTypeRepository.findByMemoTypeSeq(1L);
        if (memoTypeSeq == null) {
            saveMemoType();
        }

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

    @Transactional
    @Override
    public void deleteByMemberAndDiaryDate(CustomUserDetails details, String diaryDate) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        // 날짜 확인
        checkDateValidation(diaryDate);

        // 멤버와 날짜로 배경판을 찾기
        Background background = backgroundRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate))
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), BACKGROUND_NOT_FOUND));

        delete(background);

        // 배경판 삭제
        backgroundRepository.deleteAllInBatch(new ArrayList<>(){{
            add(background);
        }});
    }

    @Transactional
    @Override
    public void delete(Background background) {

        // 배경판으로 회원떡메에서 떡메타입과 떡메식별자 찾기
        List<MemberMemo> memberMemos = memberMemoRepository.findAllByBackground(background);
        FindMemosDto findMemosDto = find(memberMemos);

        // 떡메 아이템 삭제
        accountBookItemRepository.deleteAllInBatch(findMemosDto.getAccountBookItems());
        checklistItemRepository.deleteAllInBatch(findMemosDto.getChecklistItems());

        // 떡메 삭제
        textRepository.deleteAllInBatch(findMemosDto.getTexts());
        accountBookRepository.deleteAllInBatch(findMemosDto.getAccountBooks());
        checklistRepository.deleteAllInBatch(findMemosDto.getChecklists());

        // 회원떡메 삭제
        memberMemoRepository.deleteAllInBatch(memberMemos);
    }

    @Override
    public void checkDateValidation(String date) {

        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            throw new CustomException(LogUtil.getElement(), INVALID_DATE_FORMAT);
        }
    }

    @Transactional
    @Override
    public void saveMemoType() {
        List<MemoType> memoTypes = new ArrayList<>();
        memoTypes.add(MemoType.builder()
                .memoType("text")
                .build());
        memoTypes.add(MemoType.builder()
                .memoType("account book")
                .build());
        memoTypes.add(MemoType.builder()
                .memoType("checklist")
                .build());
        memoTypes.add(MemoType.builder()
                .memoType("image")
                .build());
        memoTypes.add(MemoType.builder()
                .memoType("video")
                .build());
        memoTypes.add(MemoType.builder()
                .memoType("sticker")
                .build());

        memoTypeRepository.saveAll(memoTypes);
    }

    @Override
    public ResDiaryDto load(CustomUserDetails details, String diaryDate) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        // 날짜 확인
        checkDateValidation(diaryDate);

        // background 존재 여부 확인
        Background background = backgroundRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate))
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), BACKGROUND_NOT_FOUND));

        List<MemberMemo> memberMemos = memberMemoRepository.findAllByBackground(background);
        FindMemosDto findMemosDto = find(memberMemos);

        List<MemoListDto> memoListDtos = new ArrayList<>();

        long id = 0L;
        for (Text text : findMemosDto.getTexts()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(text.getWidth())
                    .height(text.getHeight())
                    .x(text.getX())
                    .y(text.getY())
                    .memoTypeSeq(1)
                    .info(new HashMap<>(){{
                        put("textHeader", text.getTextHeader());
                        put("textContent", text.getTextContent());
                    }})
                    .build());
        }

        for (AccountBook accountBook : findMemosDto.getAccountBooks()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(accountBook.getWidth())
                    .height(accountBook.getHeight())
                    .x(accountBook.getX())
                    .y(accountBook.getY())
                    .memoTypeSeq(2)
                    .info(new HashMap<>(){{
                        put("totalAmount", accountBook.getTotalAmount());
                        put("totalDeposit", accountBook.getTotalDeposit());
                        put("totalWithdraw", accountBook.getTotalWithdraw());
                        put("accountBookItems", new LinkedList<AccountBookItemDto>(){{
                            for (AccountBookItem accountBookItem : findMemosDto.getAccountBookItems()) {
                                if (accountBookItem.getAccountBook().equals(accountBook)) {
                                    add(AccountBookItemDto.builder()
                                            .description(accountBookItem.getDescription())
                                            .deposit(accountBookItem.getDeposit())
                                            .withdraw(accountBookItem.getWithdraw())
                                            .build());
                                }
                            }
                        }});
                    }})
                    .build());
        }

        for (Checklist checklist : findMemosDto.getChecklists()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(checklist.getWidth())
                    .height(checklist.getHeight())
                    .x(checklist.getX())
                    .y(checklist.getY())
                    .memoTypeSeq(3)
                    .info(new HashMap<>(){{
                        put("checklistHeader", checklist.getChecklistHeader());
                        put("checklistItems", new LinkedList<ChecklistItemDto>(){{
                            for (ChecklistItem checklistItem : findMemosDto.getChecklistItems()) {
                                if (checklistItem.getChecklist().equals(checklist)) {
                                    add(ChecklistItemDto.builder()
                                            .isChecked(checklistItem.getIsChecked())
                                            .checklistItemText(checklistItem.getChecklistItemText())
                                            .build());
                                }
                            }
                        }});
                    }})
                    .build());
        }

        return ResDiaryDto.builder()
                .diaryDate(diaryDate)
                .totalCnt(memberMemos.size())
                .memoList(memoListDtos)
                .build();
    }

    @Override
    public FindMemosDto find(List<MemberMemo> memberMemos) {
        // 배경판으로 회원떡메에서 떡메타입과 떡메식별자 찾기
        List<Long> textSeqs = new ArrayList<>();
        List<Long> accountBookSeqs = new ArrayList<>();
        List<Long> checklistSeqs = new ArrayList<>();

        for (MemberMemo memberMemo : memberMemos) {
            Long memoTypeSeq = memberMemo.getMemoType().getMemoTypeSeq();
            if (memoTypeSeq == 1L) {
                textSeqs.add(memberMemo.getMemoSeq());
            } else if (memoTypeSeq == 2L) {
                accountBookSeqs.add(memberMemo.getMemoSeq());
            } else if (memoTypeSeq == 3L) {
                checklistSeqs.add(memberMemo.getMemoSeq());
            } else {
                throw new CustomException(LogUtil.getElement(), INVALID_MEMO_TYPE);
            }
        }

        // 떡메 찾기
        List<Text> texts = textRepository.findAllByTextSeqIn(textSeqs);
        List<AccountBook> accountBooks = accountBookRepository.findAllByAccountBookSeqIn(accountBookSeqs);
        List<Checklist> checklists = checklistRepository.findAllByChecklistSeqIn(checklistSeqs);

        // 떡메 아이템 찾기
        List<AccountBookItem> accountBookItems = accountBookItemRepository.findAllByAccountBookIn(accountBooks);
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByChecklistIn(checklists);

        return FindMemosDto.builder()
                .texts(texts)
                .accountBooks(accountBooks)
                .checklists(checklists)
                .accountBookItems(accountBookItems)
                .checklistItems(checklistItems)
                .build();
    }
}
