package com.mtvs.quizlog.domain.inquiryTeacher.dto;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class FindInquiryTeacherDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private User user;
}
