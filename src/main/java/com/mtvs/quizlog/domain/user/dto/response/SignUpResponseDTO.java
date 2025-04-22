package com.mtvs.quizlog.domain.user.dto.response;


import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SignUpResponseDTO {

    @NonNull
    private Long id;

    @NonNull
    private String nickname;

    @NonNull
    private String email;

    @NonNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Status status;

    @NonNull
    private LocalDateTime createdAt;

    public SignUpResponseDTO(@NonNull String nickname, @NonNull String email, @NonNull String password, @NonNull Role role, @NonNull Status status, @NonNull LocalDateTime createdAt) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }
}
