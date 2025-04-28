package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizForm {

    private Long id ;

    private String word ;
    private String answer ;

}
