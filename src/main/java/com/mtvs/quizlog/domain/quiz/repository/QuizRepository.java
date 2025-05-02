package com.mtvs.quizlog.domain.quiz.repository;

import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizDto;

import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.folder.folderbookmarks.entity.FolderBookmark;
import com.mtvs.quizlog.domain.folder.folderchapter.entity.FolderChapter;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
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

        TypedQuery<QuizDto> query = em.createQuery("select new  com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizDto(q.title , q.answer) " +
                "from Quiz q where q.user.id = :userId and q.chapter.id = :chapterId ", QuizDto.class);
        query.setParameter("userId", userId);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }

    public List<QuizForm> findAll(Long chapterId) {

        TypedQuery<QuizForm> query = em.createQuery("select new  com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm(q.title , q.answer, q.id) from Quiz q where q.chapter.id = :chapterId", QuizForm.class);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }

    public List<Quiz> findQuiz(Long chapterId) {

        TypedQuery<Quiz> query = em.createQuery("select q from Quiz q where q.chapter.id = :chapterId", Quiz.class);
        query.setParameter("chapterId", chapterId);

        return query.getResultList() ;
    }


    public Quiz findQuizById(Long quizId) {
        TypedQuery<Quiz> query = em.createQuery( "select q from Quiz q where q.id = :quizId", Quiz.class);
        query.setParameter("quizId", quizId);

        return query.getSingleResult();
    }

    // 폴더를 만든 유저의 폴더의 퀴즈를 가져옴
    public List<Quiz> findQuizzesByUserIdAndFolderBookmarkId(Long userId, int folderBookmarkId) {
        TypedQuery<Quiz> query = em.createQuery(
                "select q from Quiz q " +
                        "join fetch q.folderBookmark fc " +
                        "join fetch fc.user u " +
                        "where u.userId = :userId and fc.folderBookmarkId = :folderBookmarkId", Quiz.class
        );
        query.setParameter("userId", userId);
        query.setParameter("folderBookmarkId", folderBookmarkId);

        return query.getResultList();
    }

    public void flush() {
        em.flush();
    }

    // 수정된 챕터들 저장 (폴더와의 연결을 끊은것을 저장)
    public void saveAll(List<Quiz> quizzes) {
        for (Quiz quiz : quizzes) {
            em.merge(quiz ); // 수정된 엔티티 반영
        }
    }

    // 챕터에서 폴더챕터 찾아서 리스트로 반환
    public List<Quiz> findByFolderBookmark(FolderBookmark folderBookmark) {
        TypedQuery<Quiz> query = em.createQuery(
                "select q from Quiz q where q.folderBookmark = :folderBookmark", Quiz.class
        );
        query.setParameter("folderBookmark", folderBookmark);
        return query.getResultList();
    }

}