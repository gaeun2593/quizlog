package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuizForm {


    private Long id ;
    private Long chapterId ;
    private String word ;
    private String answer ;
    private String status ;

    public QuizForm(String word, String answer) {
        this.word = word;
        this.answer = answer;
    }

    public QuizForm(Long id, String word, String answer) {
        this.id = id;
        this.word = word;
        this.answer = answer;
    }






}
