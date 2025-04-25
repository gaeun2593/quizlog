package com.mtvs.quizlog.domain.user.dto.request;

import com.mtvs.quizlog.domain.user.entity.Status;
import lombok.Getter;

@Getter
public class DeleteUserRequestDTO {

    private Status status;
}
