package com.mtvs.quizlog.solvedQuiz.entity;


import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "user_solved_quiz")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserCheckedQuiz {

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

    
    public UserCheckedQuiz(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;

    }


}
