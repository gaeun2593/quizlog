package com.mtvs.quizlog.domain.chapter.repository;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import jakarta.persistence.EntityManager;
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

}
