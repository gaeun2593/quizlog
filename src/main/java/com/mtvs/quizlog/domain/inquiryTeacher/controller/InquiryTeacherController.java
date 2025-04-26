package com.mtvs.quizlog.domain.inquiryTeacher.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.service.InquiryTeacherService;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class InquiryTeacherController {
    private InquiryTeacherService inquiryTeacherService;

    @Autowired
    public InquiryTeacherController(InquiryTeacherService inquiryTeacherService) {
        this.inquiryTeacherService = inquiryTeacherService;
    }

    //문의페이지 조회
    @GetMapping("/support")
    public String quizList(Model model) {
        List<InquiryTeacherListDTO> inquiry = inquiryTeacherService.findAll();
        model.addAttribute("inquiryListAttribute", inquiry);
//        내부리소스
        return "inquiry/inquiryList";
    }

// 문의 세부조회
    @GetMapping("/support/{inquiryId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long inquiryId, Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        InquiryTeacherListDTO inquiryDTOS = inquiryTeacherService.findByChapterId(inquiryId,userId);
        model.addAttribute("quizList", inquiryDTOS);
        return "inquiry/inquiry";
    }
/*

//  문의 생성
    @PostMapping("/support/create")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated @ModelAttribute("requestCreateChapterDTO") RequestCreateChapterDTO requestCreateChapterDTO) {



        Chapter chapter = inquiryTeacherService.createInquiry(requestCreateChapterDTO , user);
        requestCreateChapterDTO.getQuizForm().forEach(quizForm -> quizService.createQuiz(user , quizForm, chapter));

        return "redirect:/main";
    }
//  문의 수정
//  문의 삭제


*/

}
