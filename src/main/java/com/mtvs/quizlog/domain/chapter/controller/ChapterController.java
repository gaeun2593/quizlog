package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.response.ResponseCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.quiz.dto.CreateQuizDTO;
import com.mtvs.quizlog.domain.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/main")
public class ChapterController {
    private final ChapterService chapterService;
    private final QuizService quizService;
    Logger logger = Logger.getLogger(ChapterController.class.getName());

    @Autowired
    public ChapterController(ChapterService chapterService, QuizService quizService) {
        this.chapterService = chapterService;
        this.quizService = quizService;
    }

    @GetMapping("/create-chap")
    public String chapterView(Model model) {

        model.addAttribute(new RequestCreateChapterDTO());
        //view 이름
        return "chapter/create-chap";
    }

    /*
    * 챕터 생성시 퀴즈 생성하는 로직 두가지.
    * 1번째 한 post요청에서 다처리
    * 2번째 post요청에서 한페이지로 red
    * */

    /*챕터 생성후*/
    @PostMapping("/create-chap")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated RequestCreateChapterDTO requestCreateChapterDTO, CreateQuizDTO createQuizDTO,BindingResult bindingResult , Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        model.addAttribute(new RequestCreateChapterDTO());
        if (bindingResult.hasErrors()) {
            return "redirect:/main/create-chap"; // 다시 폼으로 돌려보냄
        }
        ResponseCreateChapterDTO responseCreateChapterDTO = chapterService.createChapter(userId, requestCreateChapterDTO);
        Long chapterId =responseCreateChapterDTO.getChapterId();
        quizService.createQuiz(createQuizDTO,chapterId);
        return "redirect:/main/create-chap";
    }
/*
* 챕터뷰구현!!!!!
*
* */
/*

    public String updateRole(@AuthenticationPrincipal AuthDetails userDetails,
                             @Validated UpdateRoleRequestDTO updateRoleRequestDTO,
                             Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateRole : {}", userId);

        UpdateRoleResponseDTO updateRole = userService.updateRole(userId, updateRoleRequestDTO);
        model.addAttribute("updatedRole", updateRole);

        return "/user/my-page";
    }

*/
    /*

     @GetMapping("createChapter")
     public ModelAndView expression(ModelAndView mv) {
        *//* view 이름부터 설정 *//*
        mv.setViewName("Chapter");
        mv.addObject("createChapterDTO", new RequestCreateChapterDTO());
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
