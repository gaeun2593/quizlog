package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;

import java.time.LocalDateTime;

public class ReadAnswerDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private InquiryTeacher inquiryTeacher;
}
