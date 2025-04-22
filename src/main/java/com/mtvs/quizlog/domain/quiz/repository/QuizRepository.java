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

    Optional<Quiz> findByQuizTitleAndQuizIdNot(String title, long quizId) ;

    // 특정 유저가 만든 퀴즈 조회
    List<Quiz> findByCreatedBy(String createdBy);

    // 퀴즈 제목으로 검색
    List<Quiz> findByTitleContaining(String keyword);

    // 특정 카테고리의 퀴즈 조회
    List<Quiz> findByCategory(String category);

    // 비공개 퀴즈 제외
    List<Quiz> findByIsPublicTrue();

    // 정렬: 최신 순으로 가져오기
    List<Quiz> findAllByOrderByCreatedAtDesc();

    Optional<Quiz> findByQuizTitle(String title);
}