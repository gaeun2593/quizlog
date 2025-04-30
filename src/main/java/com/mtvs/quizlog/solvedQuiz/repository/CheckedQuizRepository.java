package com.mtvs.quizlog.solvedQuiz.repository;

import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.solvedQuiz.dto.UserCheckedQuizDTO;
import com.mtvs.quizlog.solvedQuiz.entity.UserCheckedQuiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mtvs.quizlog.solvedQuiz.entity.Status.COMPLETED;

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

    public Chapter findChapter(long id) {
        return em.find(Chapter.class, id);
    }


    public UserCheckedQuizDTO findCheckdQuiz(long chapterId, long userId) {
        TypedQuery<Long> query = em.createQuery("select count(q) from UserCheckedQuiz q where q.chapter.id = :chapterId and q.user.id = :userId and q.status =: status", long.class);
        query.setParameter("chapterId" , chapterId);
        query.setParameter("userId" , userId) ;
        query.setParameter("status" , COMPLETED) ;
        TypedQuery<Long> totalQuery = em.createQuery(
                "select count(q) from Chapter c join c.quizzes q where c.id = :chapterId", Long.class);
        totalQuery.setParameter("chapterId", chapterId);
        Long quizCount = totalQuery.getSingleResult();

        query.setParameter("chapterId" , chapterId);
        query.setParameter("userId" , userId);
        totalQuery.setParameter("chapterId", chapterId);


        return new UserCheckedQuizDTO(query.getSingleResult(),  totalQuery.getSingleResult()) ;




    }

    public List<QuizForm> findCheckdQuizs(long chapterId, long userId) {
        TypedQuery<QuizForm> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.QuizForm(q.quiz.id, q.quiz.title, q.quiz.answer) " +
                "from UserCheckedQuiz q where q.chapter.id = :chapterId and q.user.id = :userId", QuizForm.class);
        query.setParameter("chapterId" , chapterId);
        query.setParameter("userId" , userId);

        return query.getResultList() ;
    }
}
