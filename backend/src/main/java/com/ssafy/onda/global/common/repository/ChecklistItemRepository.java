package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.Checklist;
import com.ssafy.onda.global.common.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {

    List<ChecklistItem> findAllByChecklistIn(List<Checklist> checklists);

    List<ChecklistItem> findByChecklist(Checklist checklist);

}
