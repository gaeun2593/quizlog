package com.mtvs.quizlog.domain.user.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
public class UpdateNicknameResponseDTO {

    @NotNull
    private String nickname;

    @NotNull
    private LocalDate updatedDate;
}
