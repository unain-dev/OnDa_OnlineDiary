package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {

    List<Checklist> findAllByChecklistSeqIn(List<Long> checklistSeqs);

}
