package com.mtvs.quizlog.domain.inquiryTeacher.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.repository.InquiryTeacherRepository;
import com.mtvs.quizlog.domain.inquiryTeacher.service.InquiryTeacherService;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
@Slf4j
public class InquiryTeacherController {
    private InquiryTeacherService inquiryTeacherService;
    private UserService userService;
    private InquiryTeacherRepository inquiryTeacherRepository;

    @Autowired
    public InquiryTeacherController(InquiryTeacherService inquiryTeacherService, UserService userService,InquiryTeacherRepository inquiryTeacherRepository) {
        this.inquiryTeacherService = inquiryTeacherService;
        this.userService = userService;
        this.inquiryTeacherRepository = inquiryTeacherRepository;
    }

    //문의페이지 조회
    @GetMapping("/support")
    public String quizList(@AuthenticationPrincipal AuthDetails userDetails ,Model model) {
        List<InquiryTeacherListDTO> inquiry = inquiryTeacherService.findAll(userDetails.getLogInDTO().getUserId());
        model.addAttribute("inquiryList", inquiry);
//        내부리소스
        return "inquiry/inquiryList";
    }
/*
*  @GetMapping("/chapters/{chapterId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long chapterId, Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        List<QuizDto> quizDto = quizService.findbyQuizes(userId, chapterId);
        model.addAttribute("chapterId" , chapterId);
        model.addAttribute("quizList", quizDto);
        return "quiz/quizList";
    }

*
* */
// 문의세부조회
    @GetMapping("/support/{inquiryId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long inquiryId, Model model) {
//        Long teacherId = userDetails.getLogInDTO().getUserId();
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        return "inquiry/inquiry";
    }


//  문의 생성
    @GetMapping("/support/create")
    public String addQuizPage(Model model) {
        InquiryTeacherDTO inquiryTeacherDTO = new InquiryTeacherDTO();
        model.addAttribute("inquiryTeacherDTO", inquiryTeacherDTO);
        return "inquiry/create";
    }

    @PostMapping("/support/create")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.createInquiry(inquiryTeacherDTO,user);
        return "redirect:/support";
    }
//  문의 수정
    @GetMapping("/support/{inquiryId}/edit")
    public String getUpdatePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO,Model model) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        return "inquiry/edit";
    }

    @PatchMapping ("/support/{inquiryId}/edit")
    public String updatePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.updateInquiry(inquiryTeacherDTO);
        return "redirect:/support/"+inquiryId;
    }
// 태용님코드
/*

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
*/

//  문의 삭제



}
