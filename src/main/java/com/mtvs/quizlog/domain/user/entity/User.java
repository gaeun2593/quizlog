package com.mtvs.quizlog.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickname;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    protected User() {}

    public User(String nickname, String email, String password, Role role, Status status, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
