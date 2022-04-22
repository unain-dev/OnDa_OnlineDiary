package com.ssafy.onda.api.member.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"memberSeq", "memberId", "memberPw", "email", "nickname"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "memberId"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Entity
public class Member {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long memberSeq;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String memberPw;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public Member(Long memberSeq, String memberId, String memberPw, String email, String nickname) {
        this.memberSeq = memberSeq;
        this.memberId = memberId;
        this.memberPw = memberPw;
        this.email = email;
        this.nickname = nickname;
    }
}
