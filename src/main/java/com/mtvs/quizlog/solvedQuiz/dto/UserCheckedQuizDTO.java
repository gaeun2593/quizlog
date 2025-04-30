package com.mtvs.quizlog.solvedQuiz.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserCheckedQuizDTO {



    private long checkedQuiz ;

    private long total ;

    private long uncheckedQuiz ;

    public UserCheckedQuizDTO(long checkedQuiz, long total) {
        this.checkedQuiz = checkedQuiz;
        this.total = total;
        this.uncheckedQuiz = this.total - this.checkedQuiz ;

    }


}
