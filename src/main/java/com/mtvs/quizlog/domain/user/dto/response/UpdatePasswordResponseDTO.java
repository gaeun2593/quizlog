package com.mtvs.quizlog.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdatePasswordResponseDTO {

    @NotBlank
    private String newPassword;
    private LocalDateTime updatedAt;
}
