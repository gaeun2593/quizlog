package com.mtvs.quizlog.domain.chapter.repository;

import com.mtvs.quizlog.domain.chapter.dto.request.UserChapter;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ChapterRepository {

    private final EntityManager em;

    public void save(Chapter chapter) {
        em.persist(chapter);
    }

    public Optional<Chapter> find(long id) {
        Chapter chapter = em.find(Chapter.class, id);

        return Optional.ofNullable(chapter);
    }



    public List<ChapterDto> findByUserUd(User user) {
        TypedQuery<ChapterDto> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto(c.id , c.title) from Chapter c where c.user = :userId", ChapterDto.class);
        query.setParameter("userId", user);
        List<ChapterDto> chapterDto = query.getResultList();
        return chapterDto ;
    }

    public List<Chapter> findchpaterAndQuizs(Long chapterId, Long userId) {
        TypedQuery<Chapter> query = em.createQuery("select c from Chapter c join fetch c.quizzes q where c.id = :chapterId and c.user.id = :userId", Chapter.class);
        query.setParameter("chapterId", chapterId);
        query.setParameter("userId", userId);

        return  query.getResultList();

    }

    public List<UserChapter> findAll() {
        TypedQuery<UserChapter> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.UserChapter(c.id , c.title , u.nickname) from Chapter c join c.user u order by c.createdAt DESC", UserChapter.class);
        return query.getResultList();
    }

    public List<ChapterDto> findTitle(String search) {
        TypedQuery<ChapterDto> query = em.createQuery(
                "select new com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto(c.id, c.title, u.nickname) " +
                        "from Chapter c " +
                        "join c.user u " +
                        "where c.title like :search",
                ChapterDto.class
        );
        query.setParameter("search" , search + "%");
        return query.getResultList();
    }
}
