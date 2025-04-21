package com.mtvs.quizlog.domain.chapter.repository;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    Optional<Chapter> findById(Long id);
    List<Chapter> findAll();
}
