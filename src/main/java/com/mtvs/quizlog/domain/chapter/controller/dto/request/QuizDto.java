package com.mtvs.quizlog.domain.chapter.controller.dto.request;

import lombok.Data;


@Data
public class QuizDto {

    private String title  ;

    private String answer ;

    public QuizDto(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }
}
