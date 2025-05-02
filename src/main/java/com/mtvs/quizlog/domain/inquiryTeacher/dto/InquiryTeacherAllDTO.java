package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
public class InquiryTeacherAllDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Status status;
    private long user;
    private long teacher;


}
