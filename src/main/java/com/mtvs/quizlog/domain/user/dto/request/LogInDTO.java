package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.Status;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class LogInDTO {

    private Long userId;
    private String nickname;
    private String email;
    private String password;
    private Role role;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public LogInDTO(Long userId, String nickname, String email, String password, Role role, Status status, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
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

    public List<String> getRole() {
        if (this.role.getRole().length() > 0) {
            return Arrays.asList(this.role.getRole().split(","));
        }
        return new ArrayList<>();
    }
}
