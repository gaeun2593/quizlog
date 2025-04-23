package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.chapter.dto.CreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

@Controller
@RequestMapping("chapter")
public class ChapterController {
    private final ChapterService chapterService;
    Logger logger = Logger.getLogger(ChapterController.class.getName());

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }
    @GetMapping()
    public String chapterView(Model model) {
        /* view 이름부터 설정 */
        model.addAttribute(new CreateChapterDTO());

        return "chapter";
    }
    @PostMapping("createChapter")
    public String processingPost(CreateChapterDTO createChapterDTO, Model model) {
        chapterService.createChapter(createChapterDTO);
        return "redirect:chapter";
    }


    /*

     @GetMapping("createChapter")
     public ModelAndView expression(ModelAndView mv) {
        *//* view 이름부터 설정 *//*
        mv.setViewName("Chapter");
        mv.addObject("createChapterDTO", new CreateChapterDTO());
        return mv;
    }
*/

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
