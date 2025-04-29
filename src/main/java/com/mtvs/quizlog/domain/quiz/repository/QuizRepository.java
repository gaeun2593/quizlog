package com.mtvs.quizlog.domain.quiz.repository;

import com.mtvs.quizlog.domain.chapter.dto.request.QuizDto;

import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    public Optional<Quiz> find(long id){
        Quiz quiz = em.find(Quiz.class, id);
        return Optional.ofNullable(quiz);
    }

    public List<QuizDto> findbyQuizes(Long userId, Long chapterId) {

        TypedQuery<QuizDto> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.QuizDto(q.title , q.answer) " +
                "from Quiz q where q.user.id = :userId and q.chapter.id = :chapterId ", QuizDto.class);
        query.setParameter("userId", userId);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }

    public List<QuizForm> findAll(Long chapterId) {

        TypedQuery<QuizForm> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.QuizForm(q.title , q.answer) from Quiz q where q.chapter.id = :chapterId", QuizForm.class);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }

    public List<Quiz> findQuiz(Long chapterId) {

        TypedQuery<Quiz> query = em.createQuery("select q from Quiz q where q.chapter.id = :chapterId", Quiz.class);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }
}