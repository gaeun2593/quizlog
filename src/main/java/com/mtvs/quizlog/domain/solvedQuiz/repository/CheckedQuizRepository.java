package com.mtvs.quizlog.domain.solvedQuiz.repository;

import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.solvedQuiz.dto.UserCheckedChapterDTO;
import com.mtvs.quizlog.domain.solvedQuiz.dto.UserCheckedQuizDTO;
import com.mtvs.quizlog.domain.solvedQuiz.entity.UserCheckedQuiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        TypedQuery<Object[]> query = em.createQuery(
                "SELECT " +
                        "SUM(CASE WHEN q.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
                        "SUM(CASE WHEN q.status = 'INCOMPLETE' THEN 1 ELSE 0 END), " +
                        "COUNT(q) " +
                        "FROM UserCheckedQuiz q " +
                        "WHERE q.chapter.id = :chapterId AND q.user.id = :userId", Object[].class);

        query.setParameter("chapterId", chapterId);
        query.setParameter("userId", userId);

        Object[] result = query.getSingleResult();

        Long completeCount = (Long) result[0];
        log.info("completeCount = {}", completeCount);
        Long uncompletedCount = (Long) result[1];
        Long totalCount = (Long) result[2] ;


        return new UserCheckedQuizDTO(completeCount, uncompletedCount, totalCount);




    }

    public List<QuizForm> findCheckdQuizs(long chapterId, long userId) {
        TypedQuery<QuizForm> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm(q.quiz.id, q.quiz.title, q.quiz.answer) " +
                "from UserCheckedQuiz q where q.chapter.id = :chapterId and q.user.id = :userId", QuizForm.class);
        query.setParameter("chapterId" , chapterId);
        query.setParameter("userId" , userId);

        return query.getResultList() ;
    }

    public List<UserCheckedChapterDTO> findCheckedChapters(long userId) {
        TypedQuery<UserCheckedChapterDTO> query = em.createQuery(
                "select DISTINCT new com.mtvs.quizlog.solvedQuiz.dto.UserCheckedChapterDTO(c.chapter.id, c.chapter.title, c.user.nickname) " +
                        "from UserCheckedQuiz c " +
                        "where c.user.id = :userId",
                UserCheckedChapterDTO.class
        );
        query.setParameter("userId", userId);

        return query.getResultList() ;
    }



    public List<UserCheckedChapterDTO> findChekedFolder(long folderChapterId) {
        TypedQuery<UserCheckedChapterDTO> query = em.createQuery(
                "select DISTINCT new com.mtvs.quizlog.solvedQuiz.dto.UserCheckedChapterDTO(c.id, c.title, c.user.nickname) " +
                        "from Chapter c " +
                        "where c.folderChapter.id = :folderChapterId",
                UserCheckedChapterDTO.class
        );
        query.setParameter("folderChapterId", folderChapterId);
        return query.getResultList() ;
    }

    public void removeCheckdQuiz(long chapterId, Long userId) {
        Query query = em.createQuery("delete from UserCheckedQuiz q where q.chapter.id = :chapterId and q.user.id = :userId");
        query.setParameter("chapterId", chapterId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}
