package com.mtvs.quizlog.domain.user.dto.response;


import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignUpResponseDTO {

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
}
