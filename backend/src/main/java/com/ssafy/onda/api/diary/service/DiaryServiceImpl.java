package com.ssafy.onda.api.diary.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.onda.api.diary.dto.FindMemosDto;
import com.ssafy.onda.api.diary.dto.MemoListDto;
import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.dto.response.ResDiaryDto;
import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.api.diary.repository.DiaryRepository;
import com.ssafy.onda.api.member.entity.Member;
import com.ssafy.onda.api.member.repository.MemberRepository;
import com.ssafy.onda.global.common.auth.CustomUserDetails;
import com.ssafy.onda.global.common.dto.*;
import com.ssafy.onda.global.common.entity.*;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import com.ssafy.onda.global.common.entity.embedded.Memo;
import com.ssafy.onda.global.common.repository.*;
import com.ssafy.onda.global.common.service.FileHandler;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    private final DiaryRepository diaryRepository;

    private final MemberRepository memberRepository;

    private final TextRepository textRepository;

    private final AccountBookRepository accountBookRepository;

    private final AccountBookItemRepository accountBookItemRepository;

    private final ChecklistRepository checklistRepository;

    private final ChecklistItemRepository checklistItemRepository;

    private final ImageRepository imageRepository;

    private final StickerRepository stickerRepository;

    private final FileHandler fileHandler;

    @Transactional
    @Override
    public void save(CustomUserDetails details, ReqDiaryDto reqDiaryDto, List<MultipartFile> multipartFiles) {

        try {
            saveDiary(details, reqDiaryDto, multipartFiles);
        } catch (IllegalArgumentException e) {
            // json input 형식 오류
            throw new CustomException(LogUtil.getElement(), INVALID_INPUT);
        } catch (DataIntegrityViolationException e) {
            // entity의 항목으로 null이 들어가는 경우
            throw new CustomException(LogUtil.getElement(), INVALID_DATA_FORMAT);
        }
    }

    @Transactional
    @Override
    public void saveDiary(CustomUserDetails details, ReqDiaryDto reqDiaryDto, List<MultipartFile> multipartFiles) throws IllegalArgumentException, DataIntegrityViolationException {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));

        // 날짜 확인
        String diaryDate = reqDiaryDto.getDiaryDate();
        checkDateValidation(diaryDate);

        // 저장 절차가 바뀌었다
        // 기존 : 메모지 -> 아이템 -> 멤버메모 -> 백그라운드
        // 바뀜 : 다이어리 -> 메모지 -> 아이템

        // 다이어리를 미리 저장하기 위해 먼저 삭제하면 안 되는 파일을 찾아야 한다
        Set<Image> archivedImages = new HashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        // 입력을 떡메 단위로 분류
        List<MemoListDto> memoListDtos = reqDiaryDto.getMemoList();
        if (memoListDtos.size() == 0) {
            throw new CustomException(LogUtil.getElement(), NO_MEMO_AVAILABLE);
        }

        for (MemoListDto memoListDto : memoListDtos) {
            if (memoListDto.getMemoTypeSeq() == 4) {
                String imageInfo = memoListDto.getInfo().toString();

                // 기존에 있던 파일을 그대로 가져온 경우
                if (imageInfo.startsWith("http://")) {

                    // 기존 이미지 메모지를 db에서 찾아와야 함
                    int indexOf = imageInfo.lastIndexOf(File.separator) + 1;
                    String encodedName = imageInfo.substring(indexOf);

                    Image archivedImage = imageRepository.findByFileInfoEncodedName(encodedName)
                            .orElseThrow(() -> new CustomException(LogUtil.getElement(), ENTITY_NOT_FOUND));

                    // 좌표만 업데이트
                    archivedImage.changeMemoEntity(memoListDto.getX(), memoListDto.getY(), memoListDto.getWidth(), memoListDto.getHeight());
                    archivedImages.add(archivedImage);
                }
            }
        }

        // 1. 다이어리 저장
        // 회원떡메(memberMemo), 회원떡메가 가지고 있는 떡메모지 삭제
        Optional<Diary> optionalDiary = diaryRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate));
        optionalDiary.ifPresent(diary -> deleteMemosByDiary(diary, archivedImages));
        Diary diary = optionalDiary.orElseGet(() -> diaryRepository.save(Diary.builder()
                .diaryDate(LocalDate.parse(diaryDate))
                .member(member)
                .build()));

        // 2-1. ReqDiaryDto 파헤치기
        List<Text> texts = new ArrayList<>();
        List<AccountBook> accountBooks = new ArrayList<>();
        List<Checklist> checklists = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        List<Sticker> stickers = new ArrayList<>();

        Map<AccountBook, List<AccountBookItemDto>> accountBookMap = new HashMap<>();
        Map<Checklist, List<ChecklistItemDto>> checklistMap = new HashMap<>();

        int fileCnt = 0;
        for (MemoListDto memoListDto : memoListDtos) {
            Integer memoTypeSeq = memoListDto.getMemoTypeSeq();
            if (memoTypeSeq == 1) {
                TextDto textDto = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                texts.add(Text.builder()
                        .memo(Memo.builder()
                                .x(memoListDto.getX())
                                .y(memoListDto.getY())
                                .width(memoListDto.getWidth())
                                .height(memoListDto.getHeight())
                                .build())
                        .header(textDto.getHeader())
                        .content(textDto.getContent())
                        .diary(diary)
                        .build());
            } else if (memoTypeSeq == 2) {
                List<AccountBookItemDto> accountBookItemDtos = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                AccountBook accountBook = AccountBook.builder()
                        .memo(Memo.builder()
                                .x(memoListDto.getX())
                                .y(memoListDto.getY())
                                .width(memoListDto.getWidth())
                                .height(memoListDto.getHeight())
                                .build())
                        .diary(diary)
                        .build();
                accountBooks.add(accountBook);
                accountBookMap.put(accountBook, accountBookItemDtos);
            } else if (memoTypeSeq == 3) {
                ChecklistDto checklistDto = mapper.convertValue(memoListDto.getInfo(), new TypeReference<>() {});
                Checklist checklist = Checklist.builder()
                        .memo(Memo.builder()
                                .x(memoListDto.getX())
                                .y(memoListDto.getY())
                                .width(memoListDto.getWidth())
                                .height(memoListDto.getHeight())
                                .build())
                        .checklistHeader(checklistDto.getChecklistHeader())
                        .diary(diary)
                        .build();
                checklists.add(checklist);
                checklistMap.put(checklist, checklistDto.getChecklistItems());
            } else if (memoTypeSeq == 4) {
                String imageInfo = memoListDto.getInfo().toString();

                // 새로운 파일 저장
                if (!imageInfo.startsWith("http://")) {
                    int fileIdx = Integer.parseInt(imageInfo);
                    if (multipartFiles == null) {
                        throw new CustomException(LogUtil.getElement(), NO_DATA_TO_SAVE);
                    } else if (multipartFiles.size() <= fileIdx) {
                        throw new CustomException(LogUtil.getElement(), FILE_INDEX_OUT_OF_BOUNDS);
                    }

                    try {
                        Image image = Image.builder()
                                .memo(Memo.builder()
                                        .x(memoListDto.getX())
                                        .y(memoListDto.getY())
                                        .width(memoListDto.getWidth())
                                        .height(memoListDto.getHeight())
                                        .build())
                                .fileInfo(fileHandler.parseFileInfo(multipartFiles.get(fileIdx)))
                                .diary(diary)
                                .build();

                        images.add(image);
                        fileCnt++;
                    } catch (IOException e) {
                        throw new CustomException(LogUtil.getElement(), FAIL_TO_SAVE_IMAGE);
                    }
                }

            } else if (memoTypeSeq == 5) {
                String emoji = (String) memoListDto.getInfo();
                stickers.add(Sticker.builder()
                        .memo(Memo.builder()
                                .x(memoListDto.getX())
                                .y(memoListDto.getY())
                                .width(memoListDto.getWidth())
                                .height(memoListDto.getHeight())
                                .build())
                        .emoji(emoji)
                        .diary(diary)
                        .build());
            } else {
                throw new CustomException(LogUtil.getElement(), INVALID_MEMO_TYPE);
            }
        }

        if (multipartFiles != null && multipartFiles.size() != fileCnt) {
            throw new CustomException(LogUtil.getElement(), MISMATCH_IN_NUMBER_OF_FILES_AND_IMAGES);
        }

        // 2-2. 메모지 저장
        textRepository.saveAll(texts);
        List<AccountBook> savedAccountBooks = accountBookRepository.saveAll(accountBooks);
        List<Checklist> savedChecklists = checklistRepository.saveAll(checklists);
        imageRepository.saveAll(images);
        stickerRepository.saveAll(stickers);

        // 3. 떡메 항목 저장
        for (AccountBook savedAccountBook : savedAccountBooks) {
            List<AccountBookItemDto> accountBookItemDtos = accountBookMap.get(savedAccountBook);
            List<AccountBookItem> accountBookItems = new ArrayList<>();
            for (AccountBookItemDto accountBookItemDto : accountBookItemDtos) {
                accountBookItems.add(AccountBookItem.builder()
                        .content(accountBookItemDto.getContent())
                        .income(accountBookItemDto.getIncome())
                        .outcome(accountBookItemDto.getOutcome())
                        .accountBook(savedAccountBook)
                        .build());
            }
            if (accountBookItems.size() == 0) {
                throw new CustomException(LogUtil.getElement(), NO_DATA_TO_SAVE);
            }
            accountBookItemRepository.saveAll(accountBookItems);
        }

        for (Checklist savedChecklist : savedChecklists) {
            List<ChecklistItemDto> checklistItemDtos = checklistMap.get(savedChecklist);
            List<ChecklistItem> checklistItems = new ArrayList<>();
            for (ChecklistItemDto checklistItemDto : checklistItemDtos) {
                checklistItems.add(ChecklistItem.builder()
                        .isChecked(checklistItemDto.getIsChecked())
                        .content(checklistItemDto.getContent())
                        .checklist(savedChecklist)
                        .build());
            }
            if (checklistItems.size() == 0) {
                throw new CustomException(LogUtil.getElement(), NO_DATA_TO_SAVE);
            }
            checklistItemRepository.saveAll(checklistItems);
        }
    }

    @Transactional
    @Override
    public void delete(CustomUserDetails details, String diaryDate) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
        // 날짜 확인
        checkDateValidation(diaryDate);

        // 멤버와 날짜로 배경판을 찾기
        Diary diary = diaryRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate))
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), DIARY_NOT_FOUND));

        deleteMemosByDiary(diary, new HashSet<>());
        diaryRepository.deleteAllInBatch(new ArrayList<>(){{
            add(diary);
        }});
    }

    @Transactional
    @Override
    public void deleteMemosByDiary(Diary diary, Set<Image> archivedImage) {

        // diary에 해당하는 떡메 찾기 -> 떡메 아이템 삭제 -> 떡메 삭제
        FindMemosDto findMemosDto = findByDiary(diary);

        // 떡메 아이템 삭제
        accountBookItemRepository.deleteAllInBatch(findMemosDto.getAccountBookItems());
        checklistItemRepository.deleteAllInBatch(findMemosDto.getChecklistItems());

        // 떡메 삭제
        textRepository.deleteAllInBatch(findMemosDto.getTexts());
        accountBookRepository.deleteAllInBatch(findMemosDto.getAccountBooks());
        checklistRepository.deleteAllInBatch(findMemosDto.getChecklists());
        List<Image> images = new ArrayList<>();
        for (Image image : findMemosDto.getImages()) {
            if (!archivedImage.contains(image)) {

                FileInfo fileInfo = image.getFileInfo();

                File file = new File(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedName());
                if (file.exists()) {
                    file.delete();
                } else {
                    log.info("no image available to delete");
                }
                images.add(image);
            } else {
                // 이게 필요한가?
                archivedImage.remove(image);
            }
        }
        imageRepository.deleteAllInBatch(images);
        stickerRepository.deleteAllInBatch(findMemosDto.getStickers());
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

    @Override
    public ResDiaryDto load(CustomUserDetails details, String diaryDate) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
        // 날짜 확인
        checkDateValidation(diaryDate);

        // diary 존재 여부 확인
        Diary diary = diaryRepository.findByMemberAndDiaryDate(member, LocalDate.parse(diaryDate))
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), DIARY_NOT_FOUND));
        FindMemosDto findMemosDto = findByDiary(diary);

        List<MemoListDto> memoListDtos = new ArrayList<>();

        long id = 0L;
        for (Text text : findMemosDto.getTexts()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(text.getMemo().getWidth())
                    .height(text.getMemo().getHeight())
                    .x(text.getMemo().getX())
                    .y(text.getMemo().getY())
                    .memoTypeSeq(1)
                    .info(new HashMap<>(){{
                        put("header", text.getHeader());
                        put("content", text.getContent());
                    }})
                    .build());
        }

        for (AccountBook accountBook : findMemosDto.getAccountBooks()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(accountBook.getMemo().getWidth())
                    .height(accountBook.getMemo().getHeight())
                    .x(accountBook.getMemo().getX())
                    .y(accountBook.getMemo().getY())
                    .memoTypeSeq(2)
                    .info(new LinkedList<AccountBookItemDto>(){{
                        for (AccountBookItem accountBookItem : findMemosDto.getAccountBookItems()) {
                            if (accountBookItem.getAccountBook().equals(accountBook)) {
                                add(AccountBookItemDto.builder()
                                        .content(accountBookItem.getContent())
                                        .income(accountBookItem.getIncome())
                                        .outcome(accountBookItem.getOutcome())
                                        .build());
                            }
                        }
                    }})
                    .build());
        }

        for (Checklist checklist : findMemosDto.getChecklists()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(checklist.getMemo().getWidth())
                    .height(checklist.getMemo().getHeight())
                    .x(checklist.getMemo().getX())
                    .y(checklist.getMemo().getY())
                    .memoTypeSeq(3)
                    .info(new HashMap<>(){{
                        put("checklistHeader", checklist.getChecklistHeader());
                        put("checklistItems", new LinkedList<ChecklistItemDto>(){{
                            for (ChecklistItem checklistItem : findMemosDto.getChecklistItems()) {
                                if (checklistItem.getChecklist().equals(checklist)) {
                                    add(ChecklistItemDto.builder()
                                            .isChecked(checklistItem.getIsChecked())
                                            .content(checklistItem.getContent())
                                            .build());
                                }
                            }
                        }});
                    }})
                    .build());
        }

        for (Image image : findMemosDto.getImages()) {
            FileInfo fileInfo = image.getFileInfo();

            if (fileInfo != null) {
                memoListDtos.add(MemoListDto.builder()
                        .id(id++)
                        .width(image.getMemo().getWidth())
                        .height(image.getMemo().getHeight())
                        .x(image.getMemo().getX())
                        .y(image.getMemo().getY())
                        .memoTypeSeq(4)
                        .info("http://k6a107.p.ssafy.io/" + fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedName())
                        .build());
            } else {
                throw new CustomException(LogUtil.getElement(), NO_MEMO_AVAILABLE);
            }
        }

        for (Sticker sticker : findMemosDto.getStickers()) {
            memoListDtos.add(MemoListDto.builder()
                    .id(id++)
                    .width(sticker.getMemo().getWidth())
                    .height(sticker.getMemo().getHeight())
                    .x(sticker.getMemo().getX())
                    .y(sticker.getMemo().getY())
                    .memoTypeSeq(5)
                    .info(sticker.getEmoji())
                    .build());
        }

        return ResDiaryDto.builder()
                .diaryDate(diaryDate)
                .totalCnt(memoListDtos.size())
                .memoList(memoListDtos)
                .build();
    }

    @Override
    public FindMemosDto findByDiary(Diary diary) {

        List<Text> texts = textRepository.findAllByDiary(diary);
        List<AccountBook> accountBooks = accountBookRepository.findAllByDiary(diary);
        List<Checklist> checklists = checklistRepository.findAllByDiary(diary);
        List<Image> images = imageRepository.findAllByDiary(diary);
        List<Sticker> stickers = stickerRepository.findAllByDiary(diary);

        // 떡메 아이템 찾기
        List<AccountBookItem> accountBookItems = accountBookItemRepository.findAllByAccountBookIn(accountBooks);
        List<ChecklistItem> checklistItems = checklistItemRepository.findAllByChecklistIn(checklists);

        return FindMemosDto.builder()
                .texts(texts)
                .accountBooks(accountBooks)
                .checklists(checklists)
                .images(images)
                .stickers(stickers)
                .accountBookItems(accountBookItems)
                .checklistItems(checklistItems)
                .build();
    }

    @Override
    public List<Integer> getDays(CustomUserDetails details, String diaryDate) {

        // 회원 확인
        Member member = memberRepository.findByMemberId(details.getUsername())
                .orElseThrow(() -> new CustomException(LogUtil.getElement(), MEMBER_NOT_FOUND));
        // 날짜 확인
        checkDateValidation(diaryDate);
        List<LocalDate> diaryDays = diaryRepository.findByMemberAndDiaryDateLike(member, LocalDate.parse(diaryDate));

        List<Integer> days = new ArrayList<>();
        for (LocalDate diaryDay : diaryDays) {
            days.add(Integer.valueOf(diaryDay.toString().substring(8)));
        }

        return days;
    }
}
