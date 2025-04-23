package com.mtvs.quizlog.domain.user.dto.response;


import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SignUpResponseDTO {

    @NotNull
    private Long id;

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
    private LocalDateTime createdAt;

    public SignUpResponseDTO(String nickname, String email, String password, Role role, Status status, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }
}
