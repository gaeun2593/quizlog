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

    
    // 퀴즈아이디 받아와야해서 필요
    public QuizForm(String word, String answer, Long id) {
        this.word = word;
        this.answer = answer;
        this.id = id;
    }

    public QuizForm(Long id, String word, String answer) {
        this.id = id;
        this.word = word;
        this.answer = answer;
    }






}
