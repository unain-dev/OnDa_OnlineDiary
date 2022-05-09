package com.ssafy.onda.global.common.service;

import com.ssafy.onda.global.common.entity.Image;
import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import com.ssafy.onda.global.common.repository.ImageRepository;
import com.ssafy.onda.global.common.util.LogUtil;
import com.ssafy.onda.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.ssafy.onda.global.error.dto.ErrorStatus.NO_DATA_TO_SAVE;
import static com.ssafy.onda.global.error.dto.ErrorStatus.NO_MEMO_AVAILABLE;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class FileInfoServiceImpl implements FileInfoService {

    private final ImageRepository imageRepository;

    private final FileHandler fileHandler;

    @Transactional
    @Override
    public Image save(Image image, MultipartFile multipartFile) throws IOException {

        FileInfo fileInfo = fileHandler.parseFileInfo(multipartFile);
        if (fileInfo != null) {
            return imageRepository.save(Image.builder()
                    .fileInfo(fileInfo)
                    .memo(image.getMemo())
                    .build());
        } else {
            throw new CustomException(LogUtil.getElement(), NO_DATA_TO_SAVE);
        }
    }

    @Transactional
    @Override
    public void delete(Image image) {

        FileInfo fileInfo = image.getFileInfo();

        File file = new File(fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedName());

        if (file.exists()) {
            file.delete();
        } else {
            log.info("no image available");
        }

        imageRepository.deleteAllInBatch(new ArrayList<>() {{
            add(image);
        }});
    }

    @Override
    public String loadPath(Image image) {

        FileInfo fileInfo = image.getFileInfo();

        if (fileInfo != null) {
            return "http://k6a107.p.ssafy.io/" + fileInfo.getSavedPath() + File.separator + fileInfo.getEncodedName();
        } else {
            throw new CustomException(LogUtil.getElement(), NO_MEMO_AVAILABLE);
        }
    }

    // Video 구현 시 사용할 register
//    @Transactional
//    public void register(Object mediaMemo, Long memoTypeSeq, MultipartFile multipartFile) throws IOException {
//        FileInfo fileInfo = fileHandler.parseFile(multipartFile);
//        if (fileInfo != null) {
//            if (memoTypeSeq == 4) {
//                Image image = (Image) mediaMemo;
//                imageRepository.save(Image.builder()
//                        .fileInfo(fileInfo)
//                        .memo(image.getMemo())
//                        .build());
//            }
//        }
//    }
}
