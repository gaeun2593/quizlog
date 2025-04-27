package com.mtvs.quizlog.solvedQuiz.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static jakarta.persistence.GenerationType.*;

@Entity
@Table(name = "user_solved_quiz")
public class UserSolvedQuiz {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id ;


}
