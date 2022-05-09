package com.ssafy.onda.api.filter.repository;

import com.ssafy.onda.global.common.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountbookRepository extends JpaRepository<AccountBook, Long> {
}
