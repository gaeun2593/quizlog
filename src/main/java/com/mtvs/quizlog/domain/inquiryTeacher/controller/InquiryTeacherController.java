package com.mtvs.quizlog.domain.inquiryTeacher.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacherAnswer;
import com.mtvs.quizlog.domain.inquiryTeacher.repository.InquiryTeacherRepository;
import com.mtvs.quizlog.domain.inquiryTeacher.service.InquiryTeacherService;

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
// 1. 문의 답변을 일단 안에서 조회하게한다!
// 문의세부조회
//  +답변조회
    @GetMapping("/support/{inquiryId}")
    public String inquiryView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long inquiryId, Model model) {
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        AnswerDTO answer = inquiryTeacherService.findAnswerById(inquiry);
        log.info("answer: {}", answer);
        model.addAttribute("answer", answer);
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
//  답변 생성
//  문의 생성 페이지 GET
    @GetMapping("/support/createAnswer/{inquiryId}")
    public String getAnswerPage(Model model,@PathVariable Long inquiryId) {
        try{

            InquiryTeacherDTO inquiry =inquiryTeacherService.findById(inquiryId);

            if(inquiry==null) {
//문의 에러처리
            }

            AnswerDTO answerDTO = new AnswerDTO();
            model.addAttribute("answerDTO", answerDTO);
            return "inquiry/createAnswer";

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/support/createAnswer/{inquiryId}")
    public String addAnswer(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("answerDTO") AnswerDTO answerDTO){
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
//      findById entity 반환
//      답변할 inquiry식별
        InquiryTeacher inquiry = inquiryTeacherService.findEntityById(inquiryId);
        inquiryTeacherService.createAnswer(answerDTO,inquiry);
        return "redirect:/support";
    }
  /*  @GetMapping("/support/{inquiryId}/edit")
    public String getUpdatePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO,Model model) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);
        model.addAttribute("inquiry", inquiry);
        return "inquiry/edit";
    }
*/
    //  문의 수정
    @PatchMapping ("/support/{inquiryId}/edit")
    public String updatePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.updateInquiry(inquiryTeacherDTO,inquiryId);
        return "redirect:/support/"+inquiryId;
    }
/*
    //  답변 수정
    @PatchMapping ("/support/{inquiryId}/edit")
    public String updateAnswer(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.updateInquiry(inquiryTeacherDTO,inquiryId);
        return "redirect:/support/"+inquiryId;
    }*/
//  문의 삭제
    @PatchMapping ("/support/{inquiryId}/delete")
    public String deletePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO){
        inquiryTeacherService.deleteInquiry(inquiryId);
        return "redirect:/support/";
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
