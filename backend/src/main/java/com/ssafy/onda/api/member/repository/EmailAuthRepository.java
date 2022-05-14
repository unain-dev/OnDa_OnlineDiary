package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.member.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    Optional<EmailAuth> findByEmail(String userEmail);

    Optional<EmailAuth> deleteByEmail(String byEmail);
}