package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.diary.entity.Background;
import com.ssafy.onda.api.diary.repository.BackgroundRepository;
import com.ssafy.onda.api.filter.dto.MonthMemoListDto;
import com.ssafy.onda.api.filter.dto.response.MonthFilterDto;
import com.ssafy.onda.api.member.dto.MemberDto;
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

import java.util.*;

import static com.ssafy.onda.global.error.dto.ErrorStatus.*;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FilterServiceImpl implements FilterService {

    private final MemberMemoRepository memberMemoRepository;
    private final MemberRepository memberRepository;
    private final BackgroundRepository backgroundRepository;
    private final TextRepository textRepository;
    private final AccountBookItemRepository accountBookItemRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final MemoTypeRepository memoTypeRepository;
    private final AccountBookRepository accountBookRepository;



    @Transactional
    @Override
    public List<MonthFilterDto> search(Long type, CustomUserDetails details) {

        List<Long> accountBookLists = new ArrayList<>();
        List<Long> checklistLists = new ArrayList<>();
        List<Long> textLists = new ArrayList<>();
        List<MemberMemo> memberMemos = new ArrayList<>();
        List<Background> backgrounds = new ArrayList<>();
        List<MemoType> memoTypes = new ArrayList<>();

        List<MonthMemoListDto> monthMemoListDtos = new ArrayList<>();
        List<MonthFilterDto> monthFilterDtos = new ArrayList<>();
        List<Long> backgroundSeqList = new ArrayList<>();

        Member member = memberRepository.findByMemberId(details.getUsername())
                    .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
        backgrounds = backgroundRepository.findByMember(member);

        if(type == 0) {
            List<Long> ListTemp = new ArrayList<>();
            ListTemp.add(1L);
            ListTemp.add(2L);
            ListTemp.add(3L);
            memoTypes = memoTypeRepository.findAllByMemoTypeSeqIn(ListTemp);
        } else if(type == 1 || type == 2 || type == 3){
            memoTypes = memoTypeRepository.findAllByMemoTypeSeq(type);
        } else{
            throw new CustomException(LogUtil.getElement(), INVALID_DATA_FORMAT);
        }
        memberMemos = memberMemoRepository.findAllByBackgroundInAndMemoTypeInOrderByBackgroundAsc(backgrounds, memoTypes);

        for (Background back: backgrounds) {
            backgroundSeqList.add(back.getBackgroundSeq());
        }

        int dateFilter = 0;
        for (MemberMemo memo : memberMemos) {

            if (memo.getBackground().getBackgroundSeq() != backgroundSeqList.get(dateFilter)) {
                if(textLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(1L)
                            .count(textLists.size())
                            .memoSeqList(textLists)
                            .build());
                }
                if(accountBookLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(2L)
                            .count(accountBookLists.size())
                            .memoSeqList(accountBookLists)
                            .build());
                }
                if(checklistLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(3L)
                            .count(checklistLists.size())
                            .memoSeqList(checklistLists)
                            .build());
                }

                monthFilterDtos.add(
                    MonthFilterDto.builder()
                    .diaryDate(String.valueOf(backgrounds.get(dateFilter).getDiaryDate()))
                    .monthMemoListDto(monthMemoListDtos)
                    .build()
                );

                monthMemoListDtos = new ArrayList<>();
                accountBookLists = new ArrayList<>();
                checklistLists = new ArrayList<>();
                textLists = new ArrayList<>();

                dateFilter++;

            }

            if (memo.getMemoType().getMemoTypeSeq() == 1) {
                textLists.add(memo.getMemoSeq());
            } else if (memo.getMemoType().getMemoTypeSeq() == 2) {
                accountBookLists.add(memo.getMemoSeq());
            } else if (memo.getMemoType().getMemoTypeSeq() == 3){
                checklistLists.add(memo.getMemoSeq());
            }

        }

        if(textLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(1L)
                    .count(textLists.size())
                    .memoSeqList(textLists)
                    .build());
        }
        if(accountBookLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(2L)
                    .count(accountBookLists.size())
                    .memoSeqList(accountBookLists)
                    .build());
        }
        if(checklistLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(3L)
                    .count(checklistLists.size())
                    .memoSeqList(checklistLists)
                    .build());
        }
        monthFilterDtos.add(
                MonthFilterDto.builder()
                        .diaryDate(String.valueOf(backgrounds.get(dateFilter).getDiaryDate()))
                        .monthMemoListDto(monthMemoListDtos)
                        .build()
        );

        return monthFilterDtos;
    }

    @Override
    public List<MonthFilterDto> searchBox(Long type, String keyword, CustomUserDetails details) {

        List<Long> accountBookLists = new ArrayList<>();
        List<Long> checklistLists = new ArrayList<>();
        List<Long> textLists = new ArrayList<>();
        List<MemberMemo> memberMemos = new ArrayList<>();
        List<Background> backgrounds = new ArrayList<>();
        List<Text> texts = new ArrayList<>();
        List<AccountBookItem> accountBookItems = new ArrayList<>();
        List<Checklist> checklists = new ArrayList<>();
        List<ChecklistItem> checklistItems = new ArrayList<>();

        List<MonthMemoListDto> monthMemoListDtos = new ArrayList<>();
        List<MonthFilterDto> monthFilterDtos = new ArrayList<>();
        List<MemoType> memoTypes = new ArrayList<>();

        List<Long> memoSeqList = new ArrayList<>();
        List<Long> backgroundSeqList = new ArrayList<>();

        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
        backgrounds = backgroundRepository.findByMember(member);

        if (type == 0) {
            texts = textRepository.findAllByHeaderContainsOrContentContains(keyword, keyword);
            accountBookItems = accountBookItemRepository.findAllByContentContains(keyword);
            checklists = checklistRepository.findAllByChecklistHeaderContains(keyword);
            checklistItems = checklistItemRepository.findAllByContentContainsOrChecklistIn(keyword, checklists);
            memoTypes = memoTypeRepository.findAll();

            for (Text t : texts) {
                memoSeqList.add(t.getTextSeq());
            }
            for (AccountBookItem ABI : accountBookItems) {
                memoSeqList.add(ABI.getAccountBook().getAccountBookSeq());
            }
            for (ChecklistItem CI : checklistItems) {
                memoSeqList.add(CI.getChecklist().getChecklistSeq());
            }
        } else if (type == 1) {
            texts = textRepository.findAllByHeaderContainsOrContentContains(keyword, keyword);
            memoTypes = memoTypeRepository.findAllByMemoTypeSeq(type);

            for (Text t : texts) {
                memoSeqList.add(t.getTextSeq());
            }
        } else if (type == 2) {
            accountBookItems = accountBookItemRepository.findAllByContentContains(keyword);
            memoTypes = memoTypeRepository.findAllByMemoTypeSeq(type);

            for (AccountBookItem ABI : accountBookItems) {
                memoSeqList.add(ABI.getAccountBook().getAccountBookSeq());
            }
        } else if (type == 3) {
            checklists = checklistRepository.findAllByChecklistHeaderContains(keyword);
            checklistItems = checklistItemRepository.findAllByContentContainsOrChecklistIn(keyword, checklists);
            memoTypes = memoTypeRepository.findAllByMemoTypeSeq(type);
            for (ChecklistItem CI : checklistItems) {
                memoSeqList.add(CI.getChecklist().getChecklistSeq());
            }
        } else {
            throw new CustomException(LogUtil.getElement(), INVALID_DATA_FORMAT);
        }

        memberMemos = memberMemoRepository.findAllByBackgroundInAndMemoSeqInAndMemoTypeInOrderByBackgroundAsc(backgrounds, memoSeqList, memoTypes);

        for (Background back : backgrounds) {
            backgroundSeqList.add(back.getBackgroundSeq());
        }

        int dateFilter = 0;
        for (MemberMemo memo : memberMemos) {

            if (memo.getBackground().getBackgroundSeq() != backgroundSeqList.get(dateFilter)) {
                if (textLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(1L)
                            .count(textLists.size())
                            .memoSeqList(textLists)
                            .build());
                }
                if (accountBookLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(2L)
                            .count(accountBookLists.size())
                            .memoSeqList(accountBookLists)
                            .build());
                }
                if (checklistLists.size() != 0) {
                    monthMemoListDtos.add(MonthMemoListDto.builder()
                            .memoTypeSeq(3L)
                            .count(checklistLists.size())
                            .memoSeqList(checklistLists)
                            .build());
                }

                monthFilterDtos.add(
                        MonthFilterDto.builder()
                                .diaryDate(String.valueOf(backgrounds.get(dateFilter).getDiaryDate()))
                                .monthMemoListDto(monthMemoListDtos)
                                .build()
                );

                monthMemoListDtos = new ArrayList<>();
                accountBookLists = new ArrayList<>();
                checklistLists = new ArrayList<>();
                textLists = new ArrayList<>();

                dateFilter++;

            }

            if (memo.getMemoType().getMemoTypeSeq() == 1) {
                textLists.add(memo.getMemoSeq());
            } else if (memo.getMemoType().getMemoTypeSeq() == 2) {
                accountBookLists.add(memo.getMemoSeq());
            } else if (memo.getMemoType().getMemoTypeSeq() == 3) {
                checklistLists.add(memo.getMemoSeq());
            }

        }

        if (textLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(1L)
                    .count(textLists.size())
                    .memoSeqList(textLists)
                    .build());
        }
        if (accountBookLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(2L)
                    .count(accountBookLists.size())
                    .memoSeqList(accountBookLists)
                    .build());
        }
        if (checklistLists.size() != 0) {
            monthMemoListDtos.add(MonthMemoListDto.builder()
                    .memoTypeSeq(3L)
                    .count(checklistLists.size())
                    .memoSeqList(checklistLists)
                    .build());
        }
        monthFilterDtos.add(
                MonthFilterDto.builder()
                        .diaryDate(String.valueOf(backgrounds.get(dateFilter).getDiaryDate()))
                        .monthMemoListDto(monthMemoListDtos)
                        .build()
        );

        return monthFilterDtos;
    }


    @Override
    public Object preview(CustomUserDetails details, int memoTypeSeq, String memoSeqList) {

        MemberDto memberDto = memberRepository.findMemberDtoByMemberId(details.getUsername())
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

        // 떡메 식별자와 떡메 타입 식별자로 회원 찾기
        List<Long> foundMemberSeqs = memberMemoRepository.findAllMemberSeqsByMemoTypeSeqAndMemoSeqs(memoTypeSeq, memoSeqs);
        for (Long foundMemberSeq : foundMemberSeqs) {
            if (!memberDto.getMemberSeq().equals(foundMemberSeq)) {
                throw new CustomException(LogUtil.getElement(), ACCESS_DENIED);
            }
        }

        switch (memoTypeSeq) {
            case 1:
                List<Text> texts = textRepository.findAllByTextSeqIn(memoSeqs);
                List<TextDto> textDtos = new ArrayList<>();
                for (Text text : texts) {
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
