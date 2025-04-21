package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Role;
import lombok.Getter;

@Getter
public class UpdateRoleRequestDTO {

    private Role role;
}
