package com.ssafy.onda.api.filter.repository;

import com.ssafy.onda.global.common.entity.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
