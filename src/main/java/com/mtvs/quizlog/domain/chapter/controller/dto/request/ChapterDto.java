package com.mtvs.quizlog.domain.chapter.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChapterDto {
    private Long id;
    private String title;
    private String nickname;


    // getters/setters


    public ChapterDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}