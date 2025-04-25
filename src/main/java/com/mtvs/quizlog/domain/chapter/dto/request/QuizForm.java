package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class QuizForm {

    private String word ;
    private String answer ;

}
