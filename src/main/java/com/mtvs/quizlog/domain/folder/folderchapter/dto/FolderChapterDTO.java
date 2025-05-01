package com.mtvs.quizlog.domain.folder.folderchapter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FolderChapterDTO {
    private Integer id;
    //Controller에서 유효성 검사 시 실행
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(min = 2, max = 100, message = "제목은 2글자 이상 또는 100글자 미만입니다.")
    private String title;

    public FolderChapterDTO() {
    }

    public FolderChapterDTO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    public FolderChapterDTO(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return title;
    }
}
