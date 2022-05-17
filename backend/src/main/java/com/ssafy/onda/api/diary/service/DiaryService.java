package com.ssafy.onda.api.diary.service;

import com.ssafy.onda.api.diary.dto.FindMemosDto;
import com.ssafy.onda.api.diary.dto.MemoListDto;
import com.ssafy.onda.api.diary.dto.request.ReqDiaryDto;
import com.ssafy.onda.api.diary.dto.response.ResDiaryDto;
import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.global.common.auth.CustomUserDetails;

import com.ssafy.onda.global.common.entity.Image;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface DiaryService {

    void save(CustomUserDetails details, ReqDiaryDto reqDiaryDto, List<MultipartFile> multipartFiles);

    Set<Image> getArchivedImages(List<MemoListDto> memoListDtos);

    FindMemosDto parseMemoListDto(Diary diary, List<MemoListDto> memoListDtos, List<MultipartFile> multipartFiles, int archivedImageCnt);

    void saveMemos(FindMemosDto findMemosDto);

    void deleteAlreadySavedFile(Set<FileInfo> fileInfos);

    void delete(CustomUserDetails details, String diaryDate);

    void deleteMemosByDiary(Diary diary, Set<Image> archivedImage);

    LocalDate checkDateValidation(String date);

    ResDiaryDto load(CustomUserDetails details, String diaryDate);

    FindMemosDto findByDiary(Diary diary);

    List<Integer> getDays(CustomUserDetails details, String diaryDate);

}
