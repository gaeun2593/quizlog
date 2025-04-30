package com.mtvs.quizlog.domain.chapter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChapterDto {
    private Long id;
    private String title;
    private String nickname;


    // getters/setters


    @Override
    public String toString() {
        return title;
    }
}