package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
}
