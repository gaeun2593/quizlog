package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequestDTO {

    private Role role;

    public UpdateRoleRequestDTO() {}

    public UpdateRoleRequestDTO(Role role) {
        this.role = role;
    }
}
