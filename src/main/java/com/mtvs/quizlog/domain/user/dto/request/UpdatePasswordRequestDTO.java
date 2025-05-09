package com.mtvs.quizlog.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdatePasswordRequestDTO {

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상으로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "비밀번호는 영어, 숫자, 특수문자를 포함하고 8자 이상이어야 합니다.")
    private String newPassword;

    @NotBlank
    private String passwordCheck;

    public UpdatePasswordRequestDTO() {}

    public UpdatePasswordRequestDTO(String newPassword, String passwordCheck) {
        this.newPassword = newPassword;
        this.passwordCheck = passwordCheck;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordCheck() {
        return passwordCheck;
    }

    public void setPasswordCheck(String passwordCheck) {
        this.passwordCheck = passwordCheck;
    }
}
