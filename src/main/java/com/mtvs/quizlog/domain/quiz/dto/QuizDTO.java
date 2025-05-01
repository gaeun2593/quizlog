package com.mtvs.quizlog.domain.quiz.dto;

public class QuizDTO {

    private String title;


    public QuizDTO() {
    }

    public QuizDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "QuizDTO{" +
                "title='" + title + '\'' +
                '}';
    }
}
