package com.mtvs.quizlog.domain.chapter.service;


import com.mtvs.quizlog.domain.chapter.dto.ConvertEntityToDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.request.UpdateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.response.ResponseCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Status;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
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



}
