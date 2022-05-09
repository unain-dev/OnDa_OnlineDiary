package com.ssafy.onda.global.common.service;

import com.ssafy.onda.global.common.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileInfoService {

    Image save(Image image, MultipartFile multipartFile) throws IOException;

    void delete(Image image);

    String loadPath(Image image);

}
