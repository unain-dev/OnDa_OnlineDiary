package com.ssafy.onda.global.common.service;

import com.ssafy.onda.global.common.entity.embedded.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class FileHandler {

    public FileInfo parseFileInfo(MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return null;
        }

        String absolutePath = new File("").getAbsolutePath() + File.separator;
        String path = "images" + File.separator + LocalDate.now();
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        FileInfo fileInfo = null;

        if (!multipartFile.isEmpty()) {
            String originName = multipartFile.getOriginalFilename();
            int indexOf = originName.lastIndexOf(".");
            String encodedName = UUID.randomUUID() + originName.substring(indexOf);

            fileInfo = FileInfo.builder()
                    .originName(originName)
                    .encodedName(encodedName)
                    .savedPath(path)
                    .build();

            file = new File(absolutePath + path);
            multipartFile.transferTo(new File(file, encodedName));
        }

        return fileInfo;
    }
}
