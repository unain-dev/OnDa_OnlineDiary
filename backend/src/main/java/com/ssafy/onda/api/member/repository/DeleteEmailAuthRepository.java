package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.member.entity.DeleteEmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeleteEmailAuthRepository extends JpaRepository<DeleteEmailAuth, Long> {
    DeleteEmailAuth findByEmail(String userEmail);
}
