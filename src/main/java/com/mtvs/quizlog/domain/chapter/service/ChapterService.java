package com.mtvs.quizlog.domain.chapter.service;


import com.mtvs.quizlog.domain.chapter.dto.CreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.UpdateChapterDTO;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
* 테스트 추가해주세요!!!!
*
*
* */

@Service
public class ChapterService{

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterService(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Transactional
    public CreateChapterDTO createChapter(CreateChapterDTO createChapterDTO) {
        Optional<Chapter> findChapter = chapterRepository.findByChapterTitle((createChapterDTO.getTitle()));

        if(findChapter.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 챕터 제목입니다. : " + createChapterDTO.getTitle());
        }

        Chapter chapter = new Chapter(createChapterDTO.getTitle(),createChapterDTO.getDescription());
        Chapter saveChapter =chapterRepository.save(chapter);


        return new CreateChapterDTO(saveChapter.getTitle(),saveChapter.getDescription());
    }

    @Transactional
    public UpdateChapterDTO updateChapter(Long ChapterId, CreateChapterDTO createChapterDTO) {

        Chapter chapter = chapterRepository.findById(ChapterId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Optional<Chapter> findChapter = chapterRepository.findByChapterTitleAndChapterIdNot(createChapterDTO.getTitle(), ChapterId);
        if(findChapter.isPresent()) {
            throw new IllegalArgumentException("중복되는 제목이 존재 합니다.");
        }

        chapter = Chapter.builder()
                .title(createChapterDTO.getTitle())
                .description(createChapterDTO.getDescription())
                .build();
        Chapter savedChapter = chapterRepository.save(chapter);

        return new UpdateChapterDTO(savedChapter.getChapterId(), savedChapter.getTitle(), savedChapter.getDescription());
    }

    @Transactional
    public void deleteChapter(Long chapterId) {
        boolean result = chapterRepository.existsById(chapterId);

        if(!result) {
            throw new IllegalArgumentException("챕터가 존재하지 않습니다. " + chapterId);
        }
        chapterRepository.deleteById(chapterId);
    }
/*
*
* 테스트주석입니다.
* */

}
