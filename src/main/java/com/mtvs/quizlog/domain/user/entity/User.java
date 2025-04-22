package com.mtvs.quizlog.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    private String nickname;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @NotNull
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate deletedAt;

    public User(Long userId, String nickname, String email, String password, Role role, Status status, LocalDate createdAt, LocalDate updatedAt, LocalDate deletedAt) {
        this.userId = userId;
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
