package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.chapter.dto.CreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/chapter")
@Validated
public class ChapterController {
    private final ChapterService chapterService;
    Logger logger = Logger.getLogger(ChapterController.class.getName());
    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping
    public ResponseEntity<CreateChapterDTO> createChapter(@Validated @RequestBody CreateChapterDTO createChapterDTO) {
        logger.info("post : /chapter " + createChapterDTO.getTitle());

        CreateChapterDTO savedChapter = chapterService.createChapter(createChapterDTO);

        if (savedChapter == null) {
            return ResponseEntity.status(500).body(null);
        }else{
            return ResponseEntity.ok().body(savedChapter);
        }
    }

    @GetMapping("/login")
    public void login(){

    }
/*
    @PatchMapping("/{chapterId}")
    public ResponseEntity<CDTO> updateChapter(@PathVariable("chapterId") int chapterId, @Validated @RequestBody ChapterDTO chapterDTO) {
        logger.info("patch : /chapter " +chapterId);

        ChapterDTO updateChapter = chapterService.updateChapter(chapterId, chapterDTO);

        if(updateChapter == null){
            return ResponseEntity.status(500).body(null);
        }else{
            return ResponseEntity.ok().body(updateChapter);
        }
    }


    // 삭제
    @DeleteMapping("/{chapterId}")
    public ResponseEntity<Void> deleteChapter(@PathVariable("chapterId") int chapterId) {
        logger.info("DELETE /api/chapters/{}"+ chapterId);
        chapterService.deleteChapter(chapterId);
        return ResponseEntity.noContent().build();
    }


    // 상세 조회
    @GetMapping("/{chapterId}")
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable("chapterId") int chapterId) {
        logger.info("GET /api/chapters/{}"+ chapterId);
        ChapterDTO chapter = chapterService.getChapterById(chapterId);
        return ResponseEntity.ok(chapter);
    }
    */
    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warning("Validation error: {}"+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
