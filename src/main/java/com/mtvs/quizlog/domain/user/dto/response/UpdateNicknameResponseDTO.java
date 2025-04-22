package com.mtvs.quizlog.domain.user.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class UpdateNicknameResponseDTO {

    @NotNull
    private String nickname;

    @NotNull
    private LocalDateTime updatedDate;
}
