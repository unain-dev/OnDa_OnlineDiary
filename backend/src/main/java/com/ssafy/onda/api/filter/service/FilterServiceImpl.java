package com.ssafy.onda.api.filter.service;

import com.ssafy.onda.api.member.dto.MemberDto;
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

    private final MemberRepository memberRepository;

    private final MemberMemoRepository memberMemoRepository;

    private final TextRepository textRepository;

    private final AccountBookRepository accountBookRepository;

    private final AccountBookItemRepository accountBookItemRepository;

    private final ChecklistRepository checklistRepository;

    private final ChecklistItemRepository checklistItemRepository;

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
