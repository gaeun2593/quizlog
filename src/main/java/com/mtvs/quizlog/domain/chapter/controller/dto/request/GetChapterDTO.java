package com.mtvs.quizlog.domain.chapter.controller.dto.request;


import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
public class GetChapterDTO {

    private Long id;

    private String title;
    private String description;

}
