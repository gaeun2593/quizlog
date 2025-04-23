package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SignUpRequestDTO {

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 15, message = "닉네임은 2글자 이상 15글자 이하로 입력해주세요.")
    private String nickname;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "올바른 이메일 형식을 입력하세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8글자 이상으로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "비밀번호는 영어, 숫자, 특수문자를 포함하고 8자 이상이어야 합니다.")
    private String password;

    @NotBlank
    private String passwordCheck;

    private Role role;

    protected SignUpRequestDTO() { }

    public SignUpRequestDTO(String nickname, String email, String password, String passwordCheck, Role role) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignUpRequestDTO{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                ", role=" + role +
                '}';
    }
}
