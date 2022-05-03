package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.AccountBookItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountBookItemRepository extends JpaRepository<AccountBookItem, Long> {
}
