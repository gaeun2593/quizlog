package com.mtvs.quizlog.domain.like.dto;

public class LikeDTO {

    private Long userId;
    private Long teacherId;


    public LikeDTO() {}

    public LikeDTO(Long userId, Long teacherId) {
        this.userId = userId;
        this.teacherId = teacherId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}