package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StickerRepository extends JpaRepository<Sticker, Long> {

    List<Sticker> findAllByStickerSeqIn(List<Long> stickerSeqs);

}
