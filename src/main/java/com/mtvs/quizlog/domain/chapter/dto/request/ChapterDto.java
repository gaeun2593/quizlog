package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChapterDto {
    private Long id;
    private String title;
    private String nickname;

    public ChapterDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    // getters/setters
}