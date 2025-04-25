package com.mtvs.quizlog.domain.chapter.repository;

import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class ChapterRepository {

    private final EntityManager em;

    public void save(Chapter chapter) {

        em.persist(chapter);
    }

    public List<ChapterDto> findByUserUd(User user) {
        TypedQuery<ChapterDto> query = em.createQuery("select new com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto(c.id , c.title) from Chapter c where c.user = :userId", ChapterDto.class);
        query.setParameter("userId", user);
        List<ChapterDto> chapterDto = query.getResultList();
        return chapterDto ;
    }
}
