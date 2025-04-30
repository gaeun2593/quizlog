package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizForm {

    private Long id ;

    private String word ;
    private String answer ;

    public QuizForm(String word, String answer) {
        this.word = word;
        this.answer = answer;
    }
}
