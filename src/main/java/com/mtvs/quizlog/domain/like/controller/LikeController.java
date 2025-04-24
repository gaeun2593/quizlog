package com.mtvs.quizlog.domain.like.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.like.dto.LikeDTO;
import com.mtvs.quizlog.domain.like.service.LikeService;
import com.mtvs.quizlog.domain.user.controller.UserController;
import com.mtvs.quizlog.domain.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/like")
public class LikeController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/teacher-detail/{teacherId}")
    public ModelAndView teacherDetail(@PathVariable("teacherId") Long teacherId, ModelAndView model) {
        User teacher = likeService.findTeacherById(teacherId);
        model.addObject("teacher", teacher);
        model.setViewName("/user/teacher-page");
        return model;
    }


    // 좋아요 누르기
    @PostMapping("/teacher")
    public String likeTeacher(@AuthenticationPrincipal AuthDetails authDetails,
                              @RequestParam("teacherId") Long teacherId,
                              Model model) {
        Long userId = authDetails.getLogInDTO().getUserId();
        User teacher = likeService.findTeacherById(teacherId);
        teacherId = teacher.getUserId();

        LikeDTO likeDTO = new LikeDTO(userId, teacherId);
        likeService.like(likeDTO);

        model.addAttribute("teacher", teacher);

        return "/user/teacher-page";
    }

}
