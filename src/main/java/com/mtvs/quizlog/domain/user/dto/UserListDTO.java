package com.mtvs.quizlog.domain.user.dto;

import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.Status;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
public class UserListDTO {

    private Long userId;
    private String nickname;
    private String email;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;


    public UserListDTO(Long userId, String nickname, String email, Role role, Status status, LocalDateTime createdAt) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
