package com.mtvs.quizlog.global.exception;

public class CannotModifyAdminRoleException extends RuntimeException {
    public CannotModifyAdminRoleException(String message) {
        super(message);
    }
}
