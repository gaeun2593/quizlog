package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import com.mtvs.quizlog.domain.user.entity.Role;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InquiryTeacherDTO {
    private Long inquiryTeacherId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Status status;
    private User user;
    private Chapter chapter;

}
