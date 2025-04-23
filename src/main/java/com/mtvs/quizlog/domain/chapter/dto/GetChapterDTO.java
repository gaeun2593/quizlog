package com.mtvs.quizlog.domain.chapter.dto;


import jakarta.validation.constraints.NotBlank;
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
