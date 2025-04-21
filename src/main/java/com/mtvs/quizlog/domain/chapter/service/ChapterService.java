package com.mtvs.quizlog.domain.chapter.service;


import com.mtvs.quizlog.domain.chapter.dto.ChapterDTO;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChapterService{

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    public ChapterDTO createChapter(ChapterDTO chapterDTO) {
        return chapterDTO;
    }
}
