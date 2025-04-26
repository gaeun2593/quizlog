package com.mtvs.quizlog.domain.inquiryTeacher.entity;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@Table(name = "inquires_teacher_answer")
public class InquiryTeacherAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inquiry_teacher_answer_id")
    private Long inquiryTeacherAnswerId;

    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

//FK

    //    User N:1
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;


    //    챕터아이디 N:1
    @ManyToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

    // 선생님문의아이디 1:1
    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "inquiry_teacher_id")
    InquiryTeacher inquiryTeacher;

    public InquiryTeacherAnswer() {

    }
}
