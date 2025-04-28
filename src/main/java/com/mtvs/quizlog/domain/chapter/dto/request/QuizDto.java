package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.Data;
import lombok.Getter;


@Data
public class QuizDto {

    private String title  ;

    private String answer ;

    public QuizDto(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }
}
