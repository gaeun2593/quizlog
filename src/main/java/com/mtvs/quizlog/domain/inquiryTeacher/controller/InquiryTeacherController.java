package com.mtvs.quizlog.domain.inquiryTeacher.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherAllDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;

import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;

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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
public class InquiryTeacherController {
    private final ChapterService chapterService;
    private InquiryTeacherService inquiryTeacherService;
    private UserService userService;
    private InquiryTeacherRepository inquiryTeacherRepository;

    @Autowired
    public InquiryTeacherController(InquiryTeacherService inquiryTeacherService, UserService userService, InquiryTeacherRepository inquiryTeacherRepository, ChapterService chapterService) {
        this.inquiryTeacherService = inquiryTeacherService;
        this.userService = userService;
        this.inquiryTeacherRepository = inquiryTeacherRepository;
        this.chapterService = chapterService;
    }

    //본인문의페이지 조회
    @GetMapping("/support")
    public String quizList(@AuthenticationPrincipal AuthDetails userDetails ,Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

//      학생 선생을 위한 문의목록
        List<InquiryTeacherListDTO> inquiry;
        switch (user.getRole()) {
            case ADMIN:
                // 전 체 조 회
                List<InquiryTeacherAllDTO> adminInquiryList = inquiryTeacherService.findAllByAdmin();
                model.addAttribute("inquiryList", adminInquiryList);
                break;
            case TEACHER:
                log.info("user.getRole() = " + user.getRole());
                //        선생->자신한테 쓴 문의 조회.
                inquiry = inquiryTeacherService.findAllByTeacher(userDetails.getLogInDTO().getUserId());
<<<<<<< HEAD
              //  log.info("inquiry.title() = " + inquiry.get(0).getTitle());
=======
>>>>>>> f15d0021297915f6315e30f61cbe225005337138
                model.addAttribute("inquiryList", inquiry);
                break;
            case STUDENT:
                //        학생 -> 자신이 쓴 문의 조회.
                inquiry = inquiryTeacherService.findAll(userDetails.getLogInDTO().getUserId());
                model.addAttribute("inquiryList", inquiry);

                break;
        }
//        내부리소스
        return "inquiry/inquiryList";
    }

// 1. 문의 답변을 일단 안에서 조회하게한다!
// 문의세부조회
//  +답변조회
    @GetMapping("/support/{inquiryId}")
    public String inquiryView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long inquiryId, Model model) {
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);

        model.addAttribute("userId", userDetails.getLogInDTO().getUserId());
        model.addAttribute("inquiry", inquiry);
        log.info("inquiry: {}", inquiry.getId());
        AnswerDTO answer = inquiryTeacherService.findAnswerById(inquiry);
        log.info("answer: {}", answer);
        model.addAttribute("answer", answer);
        return "inquiry/inquiry";
    }


//  문의 생성
    /*url /teacherId를 받아올것 .*/
    /* 선생님페이지에서 버튼만들것.
    * */
    @GetMapping("/support/create/{teacherId}")
    public String addQuizPage(Model model,@PathVariable Long teacherId) {
        InquiryTeacherDTO inquiryTeacherDTO = new InquiryTeacherDTO();
        model.addAttribute("inquiryTeacherDTO", inquiryTeacherDTO);
        model.addAttribute("teacherId", teacherId);
        return "inquiry/create";
    }

    @PostMapping("/support/create/{teacherId}")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO,@PathVariable Long teacherId) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        User teacher = userService.findUser(teacherId);
//      로깅 나중에 지우기.
        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.createInquiry(inquiryTeacherDTO,user,teacher);
        return "redirect:/support";
    }
//  답변 생성
//  답변 생성 페이지 GET
    @GetMapping("/support/createAnswer/{inquiryId}")
    public String getAnswerPage(Model model,@PathVariable Long inquiryId) {
        try{
            InquiryTeacherDTO inquiry =inquiryTeacherService.findById(inquiryId);
            log.info("inquiry = {}", inquiry);
            if(inquiry==null) {
//  문의 에러처리
            }
            AnswerDTO answerDTO = new AnswerDTO();
            model.addAttribute("inquiryId", inquiryId);
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
        inquiryTeacherService.createAnswer(answerDTO,inquiry,user);
        return "redirect:/support";
    }

    //  문의 수정
    @PatchMapping ("/support/update/{inquiryId}")
    public String updatePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("inquiryTeacherDTO") InquiryTeacherDTO inquiryTeacherDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());

        log.info("inquiryTeacherDTO = {}", inquiryTeacherDTO.getContent());
        inquiryTeacherService.updateInquiry(inquiryTeacherDTO,inquiryId);
        return "redirect:/support/"+inquiryId;
    }

    //  문의 삭제
    @PatchMapping ("/support/{inquiryId}/delete")
    public String deletePost(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId){
        inquiryTeacherService.deleteInquiry(inquiryId);
        return "redirect:/support";
    }
    //  답변 삭제
    @PatchMapping ("/support/{inquiryId}/answerDelete")
    public String deleteAnswer(@AuthenticationPrincipal AuthDetails userDetails, @PathVariable Long inquiryId, @Validated @ModelAttribute("answerDTO") AnswerDTO answerDTO) {
        InquiryTeacherDTO inquiry = inquiryTeacherService.findById(inquiryId);
        inquiryTeacherService.deleteAnswer(inquiryTeacherService.findAnswerById(inquiry).getId());
        return "redirect:/support";
    }

    //  답변 수정
    @PatchMapping ("/support/updateAnswer/{inquiryId}")
    public String updateAnswer(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long inquiryId, @Validated @ModelAttribute("answerDTO") AnswerDTO answerDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        inquiryTeacherService.updateAnswer(answerDTO,inquiryId);
        return "redirect:/support/"+inquiryId;
    }

    /*@GetMapping("/support/embed/{teacherId}")
    public String inquiryListPartial(Model model, @AuthenticationPrincipal AuthDetails userDetails, @PathVariable Long teacherId) {
        // 기존 로직 유지
        Long userId = userDetails.getLogInDTO().getUserId();
        List<InquiryTeacherListDTO> inquiryList = inquiryTeacherService.findAllByTeacher(teacherId);
        model.addAttribute("inquiryList", inquiryList);
        return "inquiry/inquiryList :: inquiryTable"; // ✅ fragment만 반환
    }*/
}
