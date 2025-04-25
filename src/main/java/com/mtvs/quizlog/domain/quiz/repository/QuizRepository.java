package com.mtvs.quizlog.domain.quiz.repository;

import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuizRepository  {

    private final EntityManager em ;

    public void save(Quiz quiz){
        em.persist(quiz);
    }
}