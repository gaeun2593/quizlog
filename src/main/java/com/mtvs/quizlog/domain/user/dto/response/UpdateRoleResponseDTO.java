package com.mtvs.quizlog.domain.user.dto.response;

import com.mtvs.quizlog.domain.user.entity.Role;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class UpdateRoleResponseDTO {

    private Role role;
    private LocalDate updatedDate;
}
