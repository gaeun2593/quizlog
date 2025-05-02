package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class InquiryTeacherListDTO {
    public InquiryTeacherListDTO() {
    }
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private String userNickname;
}
