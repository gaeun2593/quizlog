package com.mtvs.quizlog.domain.solvedQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCheckedQuizDTO {


    private long checkedQuiz ;

    private long uncheckedQuiz ;

    private long total ;



}
