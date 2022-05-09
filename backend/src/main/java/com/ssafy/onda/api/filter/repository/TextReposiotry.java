package com.ssafy.onda.api.filter.repository;

import com.ssafy.onda.global.common.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextReposiotry extends JpaRepository <Text, Long> {
}
