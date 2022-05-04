package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.member.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    EmailAuth findByEmail(String userEmail);

    EmailAuth deleteByEmail(String userEmail);
}