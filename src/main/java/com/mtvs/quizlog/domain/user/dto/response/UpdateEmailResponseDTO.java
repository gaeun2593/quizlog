package com.mtvs.quizlog.domain.user.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdateEmailResponseDTO {

    @NotBlank
    private String email;

    @NotNull
    private LocalDateTime updatedDate;
}
