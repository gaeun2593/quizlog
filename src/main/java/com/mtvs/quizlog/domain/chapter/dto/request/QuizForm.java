package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizForm {

    private Long id ;

    private String word ;
    private String answer ;

    
    // 퀴즈아이디 받아와야해서 필요
    public QuizForm(String word, String answer, Long id) {
        this.word = word;
        this.answer = answer;
        this.id = id;
    }
}
