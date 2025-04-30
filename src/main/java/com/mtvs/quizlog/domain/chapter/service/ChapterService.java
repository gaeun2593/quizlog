package com.mtvs.quizlog.domain.chapter.service;


import com.mtvs.quizlog.domain.chapter.dto.request.UserChapter;
import com.mtvs.quizlog.domain.chapter.dto.request.*;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChapterService{

    private final ChapterRepository chapterRepository;


    public Chapter createChapter(RequestCreateChapterDTO requestCreateChapterDTO , User user) {
        Chapter chapter = Chapter.createChapter(user,requestCreateChapterDTO.getTitle(), requestCreateChapterDTO.getDescription(), LocalDateTime.now(), LocalDateTime.now());
        chapterRepository.save(chapter);
        return chapter;

    }

    public List<ChapterDto> findChapter(User user) {
        return chapterRepository.findByUserUd(user);

    }


    public List<Chapter> findchpaterAndQuizs(Long chapterId, Long userId) {
        return chapterRepository.findchpaterAndQuizs(chapterId,  userId);
    }

    public void updateChapter(long chapterId , String title , String description) {
        Optional<Chapter> chapter = chapterRepository.find(chapterId);
        if(!chapter.isPresent()) {
            log.info("Chapter not found");
        }
        else{
            chapter.get().setTitle(title);
            chapter.get().setDescription(description);
            chapter.get().setUpdatedAt(LocalDateTime.now());
        }

    }

    public List<UserChapter> findAll(){
       return chapterRepository.findAll();
    }

    public List<ChapterDto> findTitle(String search) {
        return chapterRepository.findTitle(search) ;
    }

    public Chapter findId(Long chapterId) {
        return chapterRepository.findChapterById(chapterId);
    }

    public List<Chapter> findChapterByFolderChapterId(Long userId, int folderChapterId) {
        return chapterRepository.findChapterByFolderChapterId(userId,folderChapterId);
    }
}
