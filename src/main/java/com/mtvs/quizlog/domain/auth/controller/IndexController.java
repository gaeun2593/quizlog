package com.mtvs.quizlog.domain.auth.controller;

import com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto;
import com.mtvs.quizlog.domain.like.service.LikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    private final LikeService likeService;

    public IndexController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/")
    public String index() { return "index"; }

//    @GetMapping("/main")
//    public String main() { return "main"; }

    // 선생님 좋아요 top5 조회
    @GetMapping("/main")
    public ModelAndView mainPage(ModelAndView model) {
        List<TeacherLikeRankingDto> topTeachers = likeService.getTop5TeachersByLikes();
        System.out.println("Top Teachers: " + topTeachers);
        model.addObject("topTeachers", topTeachers);
        model.setViewName("main");
        return model;
    }
}
