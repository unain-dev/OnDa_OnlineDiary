package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.api.diary.entity.Diary;
import com.ssafy.onda.global.common.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextRepository extends JpaRepository<Text, Long> {

    List<Text> findAllByTextSeqIn(List<Long> textSeqs);

    List<Text> findAllByDiary(Diary diary);

}
