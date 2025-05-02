package com.mtvs.quizlog.domain.chapter.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserChapter {

    private Long id ;
    private String title ;
    private String nickname ;

}
