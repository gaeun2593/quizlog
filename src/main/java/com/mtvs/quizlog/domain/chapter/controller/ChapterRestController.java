package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.chapter.dto.CreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/chapter/")
public class ChapterRestController {
    private final ChapterService chapterService;
    Logger logger = Logger.getLogger(ChapterRestController.class.getName());
    @Autowired
    public ChapterRestController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping("createChapter")
    public ResponseEntity<CreateChapterDTO> createChapter(@RequestBody CreateChapterDTO createChapterDTO) {
        CreateChapterDTO savedChapter = chapterService.createChapter(createChapterDTO);

        if (savedChapter == null) {
            System.out.println("안녕 ㅋ");
            return ResponseEntity.status(500).body(null);
        }else{
            return ResponseEntity.ok().body(savedChapter);
        }
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
