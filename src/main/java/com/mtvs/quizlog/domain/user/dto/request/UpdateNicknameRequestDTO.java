package com.mtvs.quizlog.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateNicknameRequestDTO {

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 15, message = "닉네임은 2글자 이상 15글자 이하로 입력해주세요.")
    private String nickname;

    public UpdateNicknameRequestDTO() { }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    } // 커밋
}
