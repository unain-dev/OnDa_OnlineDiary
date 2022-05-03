package com.ssafy.onda.global.common.repository;

import com.ssafy.onda.global.common.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Long> {
}
