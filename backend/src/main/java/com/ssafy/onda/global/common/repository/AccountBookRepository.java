package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookRepository  extends JpaRepository<AccountBook, Long> {
}
