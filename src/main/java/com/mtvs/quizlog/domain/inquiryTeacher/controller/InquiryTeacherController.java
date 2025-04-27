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
    private ChapterService chapterService;
    @Autowired
    public InquiryTeacherController(InquiryTeacherService inquiryTeacherService, UserService userService, ChapterService chapterService) {
        this.inquiryTeacherService = inquiryTeacherService;
        this.userService = userService;
        this.chapterService = chapterService;
    }

    //문의페이지 조회
    @GetMapping("/support")
    public String quizList(Model model) {
        List<InquiryTeacherListDTO> inquiry = inquiryTeacherService.findAll();
        model.addAttribute("inquiryList", inquiry);
//        내부리소스
        return "inquiry/inquiryList";
    }

// 문의 세부조회
    @GetMapping("/support/{inquiryId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long inquiryId, Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        InquiryTeacherListDTO inquiryDTOS = inquiryTeacherService.findByChapterId(inquiryId,userId);
        model.addAttribute("inquiry", inquiryDTOS);
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
//  문의 삭제



}
