package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByMemberId(String memberId);

    boolean existsByEmail(String email);

}
