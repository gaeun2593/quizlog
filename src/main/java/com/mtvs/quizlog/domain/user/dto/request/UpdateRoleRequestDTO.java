package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Role;

public class UpdateRoleRequestDTO {

    private Role role;

    public UpdateRoleRequestDTO() {}

    public UpdateRoleRequestDTO(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
