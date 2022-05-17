package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.diary.repository.DiaryRepository;
import com.ssafy.onda.api.member.entity.Member;
import com.ssafy.onda.api.filter.dto.MonthMemoListDto;
import com.ssafy.onda.api.filter.dto.response.MonthFilterDto;
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

import java.util.*;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FilterServiceImpl implements FilterService {

    private final MemberRepository memberRepository;

    private final TextRepository textRepository;

    private final AccountBookItemRepository accountBookItemRepository;

    private final ChecklistRepository checklistRepository;

    private final ChecklistItemRepository checklistItemRepository;

    private final AccountBookRepository accountBookRepository;

    private final DiaryRepository diaryRepository;

    @Override
    public List<MonthFilterDto> search(int memoTypeSeq, String keyword, CustomUserDetails details) {

        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        List<Diary> diarys = diaryRepository.findByMemberOrderByDiarySeqAsc(member);
        if (diarys.size() == 0) {
            throw new CustomException(LogUtil.getElement(), DIARY_NOT_FOUND);
        }

        List<MonthFilterDto> monthFilterDtos = new ArrayList<>();

        for (Diary diary : diarys) {

            List<MonthMemoListDto> monthMemoListDtos = new ArrayList<>();

            switch (memoTypeSeq) {
                case 0:
                    MonthMemoListDto monthMemoText0 = getMonthMemoText(diary, keyword);
                    if (monthMemoText0.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoText0);
                    }
                    MonthMemoListDto monthMemoAccountBook0 = getMonthMemoAccountBook(diary, keyword);
                    if (monthMemoAccountBook0.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoAccountBook0);
                    }
                    MonthMemoListDto monthMemoChecklist0 = getMonthMemoChecklist(diary, keyword);
                    if (monthMemoChecklist0.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoChecklist0);
                    }
                    break;
                case 1:
                    MonthMemoListDto monthMemoText1 = getMonthMemoText(diary, keyword);
                    if (monthMemoText1.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoText1);
                    }
                    break;
                case 2:
                    MonthMemoListDto monthMemoAccountBook2 = getMonthMemoAccountBook(diary, keyword);
                    if (monthMemoAccountBook2.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoAccountBook2);
                    }
                    break;
                case 3:
                    MonthMemoListDto monthMemoChecklist3 = getMonthMemoChecklist(diary, keyword);
                    if (monthMemoChecklist3.getCount() != 0) {
                        monthMemoListDtos.add(monthMemoChecklist3);
                    }
                    break;
                default:
                    throw new CustomException(LogUtil.getElement(), INVALID_MEMO_TYPE);
            }

            if (monthMemoListDtos.size() != 0) {
                monthFilterDtos.add(MonthFilterDto.builder()
                        .diaryDate(String.valueOf(diary.getDiaryDate()))
                        .monthMemoListDto(monthMemoListDtos)
                        .build());
            }
        }
        return monthFilterDtos;
    }

    @Override
    public MonthMemoListDto getMonthMemoText(Diary diary, String keyword) {

        List<Long> textSeqs = diaryRepository.findTextSeqsByDiaryAndKeyword(diary, keyword);

        return MonthMemoListDto.builder()
                .memoTypeSeq(1L)
                .count(textSeqs.size())
                .memoSeqList(textSeqs)
                .build();
    }

    @Override
    public MonthMemoListDto getMonthMemoAccountBook(Diary diary, String keyword) {

        List<Long> accountBookSeqs = diaryRepository.findAccountBookSeqsByDiaryAndKeyword(diary, keyword);

        return MonthMemoListDto.builder()
                .memoTypeSeq(2L)
                .count(accountBookSeqs.size())
                .memoSeqList(accountBookSeqs)
                .build();
    }

    @Override
    public MonthMemoListDto getMonthMemoChecklist(Diary diary, String keyword) {

        List<Long> checklistSeqs = diaryRepository.findChecklistSeqsByDiaryAndKeyword(diary, keyword);

        return MonthMemoListDto.builder()
                .memoTypeSeq(3L)
                .count(checklistSeqs.size())
                .memoSeqList(checklistSeqs)
                .build();
    }

    @Override
    public Object preview(CustomUserDetails details, int memoTypeSeq, String memoSeqList) {

        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        Object memoList = null;
        List<Long> memoSeqs = new ArrayList<>();

        StringTokenizer stringTokenizer = new StringTokenizer(memoSeqList, ",");
        try {
            while (stringTokenizer.hasMoreTokens()) {
                memoSeqs.add(Long.valueOf((stringTokenizer.nextToken())));
            }
        } catch (NumberFormatException e) {
            throw new CustomException(LogUtil.getElement(), INVALID_INPUT_VALUE);
        }

        if (memoSeqs.size() == 0) {
            throw new CustomException(LogUtil.getElement(), INVALID_INPUT_VALUE);
        }

        switch (memoTypeSeq) {
            case 1:
                List<Text> texts = textRepository.findAllByTextSeqIn(memoSeqs);
                List<TextDto> textDtos = new ArrayList<>();
                for (Text text : texts) {
                    if (!text.getDiary().getMember().equals(member)) {
                        throw new CustomException(LogUtil.getElement(), ACCESS_DENIED);
                    }
                    textDtos.add(TextDto.builder()
                            .header(text.getHeader())
                            .content(text.getContent())
                            .build());
                }
                memoList = textDtos;
                break;

            case 2:
                List<AccountBook> accountBooks = accountBookRepository.findAllByAccountBookSeqIn(memoSeqs);
                List<AccountBookDto> accountBookDtos = new ArrayList<>();
                for (AccountBook accountBook : accountBooks) {
                    if (!accountBook.getDiary().getMember().equals(member)) {
                        throw new CustomException(LogUtil.getElement(), ACCESS_DENIED);
                    }
                    accountBookDtos.add(AccountBookDto.builder()
                            .accountBookItems(new ArrayList<>() {{
                                for (AccountBookItem accountBookItem : accountBookItemRepository.findByAccountBook(accountBook)) {
                                    add(AccountBookItemDto.builder()
                                            .content(accountBookItem.getContent())
                                            .income(accountBookItem.getIncome())
                                            .outcome(accountBookItem.getOutcome())
                                            .build());
                                }
                            }})
                            .build());
                }
                memoList = accountBookDtos;
                break;

            case 3:
                List<Checklist> checklists = checklistRepository.findAllByChecklistSeqIn(memoSeqs);
                List<ChecklistDto> checklistDtos = new ArrayList<>();
                for (Checklist checklist : checklists) {
                    if (!checklist.getDiary().getMember().equals(member)) {
                        throw new CustomException(LogUtil.getElement(), ACCESS_DENIED);
                    }
                    checklistDtos.add(ChecklistDto.builder()
                            .checklistHeader(checklist.getChecklistHeader())
                            .checklistItems(new ArrayList<>() {{
                                for (ChecklistItem checklistItem : checklistItemRepository.findByChecklist(checklist)) {
                                    add(ChecklistItemDto.builder()
                                            .isChecked(checklistItem.getIsChecked())
                                            .content(checklistItem.getContent())
                                            .build());
                                }
                            }})
                            .build());
                }
                memoList = checklistDtos;
                break;

            default:
                throw new CustomException(LogUtil.getElement(), INVALID_MEMO_TYPE);
        }

        return memoList;
    }
}
