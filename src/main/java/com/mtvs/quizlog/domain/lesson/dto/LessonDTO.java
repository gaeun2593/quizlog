package com.mtvs.quizlog.domain.lesson.dto;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.lesson.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private User user;
    private String title;
    private String description;
    private Status status;
    private List<Chapter> chapterList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


}
