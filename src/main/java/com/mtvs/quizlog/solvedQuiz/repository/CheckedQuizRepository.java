package com.mtvs.quizlog.solvedQuiz.repository;

import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.solvedQuiz.entity.UserCheckedQuiz;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CheckedQuizRepository {

    private final EntityManager em ;

    public void save(UserCheckedQuiz checkedQuiz) {
        em.persist(checkedQuiz);
    }

    public Quiz findQuiz(long id) {
        return em.find(Quiz.class, id);
    }

    public User findUser(long id) {
        return em.find(User.class, id);
    }


}
