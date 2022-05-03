package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountBookRepository  extends JpaRepository<AccountBook, Long> {

    List<AccountBook> findAllByAccountBookSeqIn(List<Long> accountBookSeqs);

}
