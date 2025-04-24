package com.mtvs.quizlog.domain.user.entity;

public enum Role {

    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private String role;

    Role(String role) {this.role = role;}

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
