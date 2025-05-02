package com.mtvs.quizlog.domain.solvedQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCheckedChapterDTO {

    private long chapterId ;
    private String chapterName ;
    private String username ;
}
