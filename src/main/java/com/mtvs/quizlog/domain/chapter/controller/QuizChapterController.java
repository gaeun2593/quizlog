package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.auth.service.AuthService;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.dto.response.ResponseCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.quiz.dto.CreateQuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.service.QuizService;
import com.mtvs.quizlog.domain.user.dto.LogInDTO;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/main")
@Slf4j
@RequiredArgsConstructor
public class QuizChapterController {

    private final ChapterService chapterService;
    private final QuizService quizService;
    private final UserService userService;

    @GetMapping("/create-chap")
    public String chapterView(Model model) {

        RequestCreateChapterDTO dto = new RequestCreateChapterDTO();
/*
* commit
* */
        dto.getQuizForm().add(new QuizForm());
        dto.getQuizForm().add(new QuizForm());

        model.addAttribute("requestCreateChapterDTO", dto);

        return "chapter/create-chap";
    }

    @PostMapping("/create-chap")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated @ModelAttribute("requestCreateChapterDTO") RequestCreateChapterDTO requestCreateChapterDTO) {
        Long userId = userDetails.getLogInDTO().getUserId();

        User user = userService.findUser(userId);

        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getTitle());
        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getDescription());
        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getQuizForm().get(0).getWord());

        Chapter chapter = chapterService.createChapter(requestCreateChapterDTO , user);
        requestCreateChapterDTO.getQuizForm().forEach(quizForm -> quizService.createQuiz(user , quizForm, chapter));

        return "redirect:/main";
    }
   // /main/chapterList
    @GetMapping("/chapterList")
    public String quizList(@AuthenticationPrincipal AuthDetails userDetails ,Model model) {

        Long userId = userDetails.getLogInDTO().getUserId();

        User user = userService.findUser(userId);

        List<ChapterDto> chapter = chapterService.findChapter(user);

        model.addAttribute("chapterList", chapter);
        return "chapter/chapterList";
    }

    @GetMapping("/chapters/{chapterId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long chapterId, Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        List<QuizDto> quizDto = quizService.findbyQuizes(userId, chapterId);
        model.addAttribute("chapterId" , chapterId);
        model.addAttribute("quizList", quizDto);
        return "quiz/quizList";
    }

    @GetMapping("/{chapterId}/edit")
    public String edit(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long chapterId , Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        RequestCreateChapterDTO dto = new RequestCreateChapterDTO();


        List<Chapter> chapter = chapterService.findchpaterAndQuizs(chapterId, userId);

        for (Chapter c : chapter) {
            dto.setTitle(c.getTitle());
            dto.setChapterId(c.getId());
            dto.setDescription(c.getDescription());
            List<Quiz> quizzes = c.getQuizzes();
            for (Quiz quiz : quizzes) {
                dto.getQuizForm().add(new QuizForm(quiz.getId(), quiz.getTitle(), quiz.getAnswer()));
            }

        }




        model.addAttribute("requestCreateChapterDTO", dto);
        return "chapter/editForm";
    }

    @PostMapping("/{chapterId}/edit")
    public String editPost(@PathVariable Long chapterId ,@Validated @ModelAttribute("requestCreateChapterDTO") RequestCreateChapterDTO requestCreateChapterDTO) {

        for (QuizForm quizForm : requestCreateChapterDTO.getQuizForm()) {
            log.info("QuizForm: {}", quizForm.getId());
            log.info("QuizForm: {}", quizForm.getWord());
        }
        chapterService.updateChapter(chapterId , requestCreateChapterDTO.getTitle() , requestCreateChapterDTO.getDescription()) ;
        quizService.updateQuiz(chapterId ,requestCreateChapterDTO.getQuizForm());


        return "redirect:/main";
    }





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

