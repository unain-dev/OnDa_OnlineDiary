package com.ssafy.onda.api.member.repository;

import com.ssafy.onda.api.member.dto.MemberDto;
import com.ssafy.onda.api.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByMemberId(String memberId);

    boolean existsByEmail(String email);
    
    Optional<MemberDto> findMemberDtoByMemberId(String memberId);

    Optional<Member> findByMemberId(String memberId);
    
}
