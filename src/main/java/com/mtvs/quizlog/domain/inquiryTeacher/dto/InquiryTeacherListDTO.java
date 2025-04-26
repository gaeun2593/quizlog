package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

@AllArgsConstructor
@Builder
public class InquiryTeacherListDTO {
    public InquiryTeacherListDTO() {
    }
    private Long inquiryTeacherId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private String userNickname;
    private String chapterTitle;
}
