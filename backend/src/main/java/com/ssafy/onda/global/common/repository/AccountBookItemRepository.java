package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.AccountBook;
import com.ssafy.onda.global.common.entity.AccountBookItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountBookItemRepository extends JpaRepository<AccountBookItem, Long> {

    List<AccountBookItem> findAllByAccountBookIn(List<AccountBook> accountBooks);

}
