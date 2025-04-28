package com.mtvs.quizlog.solvedQuiz.entity;


import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "user_solved_quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserSolvedQuiz {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_solved_quiz_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @Enumerated(EnumType.STRING)
    private Status status;


    public UserSolvedQuiz(User user, Quiz quiz, Status status, Chapter chapter) {
        this.user = user;
        this.quiz = quiz;
        this.status = status;
        this.chapter = chapter;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
