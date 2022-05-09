package com.ssafy.onda.api.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"emailAuthSeq", "email", "emailAuth", "date"})
@Table(name = "tb_email_auth")
@Entity
public class DeleteEmailAuth {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long emailAuthSeq;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String emailAuth;

    @Column(nullable = false)
    private LocalDateTime date;

    @Builder
    public DeleteEmailAuth(String email, String emailAuth, LocalDateTime date) {
        this.emailAuthSeq = emailAuthSeq;
        this.email = email;
        this.emailAuth = emailAuth;
        this.date = date;
    }

}
