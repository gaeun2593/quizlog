package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.Data;

@Data
public class ChapterDto {
    private Long id;
    private String title;

    public ChapterDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    // getters/setters
}