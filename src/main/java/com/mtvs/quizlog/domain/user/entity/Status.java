package com.mtvs.quizlog.domain.user.entity;

public enum Status {

    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private String status;

    Status(String status) { this.status = status; }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "status='" + status + '\'' +
                '}';
    }
}
