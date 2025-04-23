package com.mtvs.quizlog.domain.user.dto.response;

import com.mtvs.quizlog.domain.user.entity.Status;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class DeleteUserResponseDTO {

    private Status status;
    private LocalDateTime deletedAt;
}
