package com.mtvs.quizlog.domain.quiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuizDTO {
    private Long id;

    public QuizDTO(String title, String answer) {
        this.title = title;
        this.answer = answer;
    }

    @NotBlank(message = "문제는 필수 입력 값입니다.")
    @Size(min = 2, max = 100, message = "문제은 2글자 이상 또는 100글자 미만입니다.")
    private String title;


    @NotBlank(message = "답은 필수 입력 항목입니다.")
    @Size(min = 1, max = 100, message = "답은 1자 이상 100자 이하로 입력해주세요")
    private String answer;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }
}
