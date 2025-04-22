package com.mtvs.quizlog.domain.quiz.repository;

import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    Optional<Quiz> findByTitleAndIdNot(String title, long quizId) ;


    // 퀴즈 제목으로 검색
    List<Quiz> findByTitleContaining(String keyword);

    // 정렬: 최신 순으로 가져오기
    List<Quiz> findAllByOrderByCreatedAtDesc();

    Optional<Quiz> findByTitle(String title);
}