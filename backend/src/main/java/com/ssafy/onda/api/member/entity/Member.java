package com.ssafy.onda.api.member.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@ToString(of = {"memberSeq", "memberId", "password", "email", "nickname"})
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
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Builder
    public Member(Long memberSeq, String memberId, String password, String email, String nickname) {
        this.memberSeq = memberSeq;
        this.memberId = memberId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
