package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findAllByImageSeqIn(List<Long> imageSeqs);

    Optional<Image> findByFileInfoEncodedName(String encodedName);

}
