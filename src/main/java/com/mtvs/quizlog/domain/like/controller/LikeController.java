package com.mtvs.quizlog.domain.like.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.like.dto.LikeDTO;
import com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto;
import com.mtvs.quizlog.domain.like.service.LikeService;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // 선생님 상세 페이지
    @GetMapping("/teacher-detail/{teacherId}")
    public ModelAndView teacherDetail(@PathVariable("teacherId") Long teacherId,
                                      @AuthenticationPrincipal AuthDetails authDetails,
                                      ModelAndView model) {
        Long userId = authDetails.getLogInDTO().getUserId();

        User teacher = likeService.findTeacherById(teacherId);
        boolean liked = likeService.hasLiked(userId, teacherId); // 현재 사용자가 이 선생님을 좋아요 눌렀는지
        int likeCount = likeService.getLikeCount(teacherId); // 선생님 총 좋아요 수

        model.addObject("teacher", teacher);
        model.addObject("liked", liked);
        model.addObject("likeCount", likeCount);
        model.setViewName("/user/teacher-page");
        return model;
    }

    // 좋아요/좋아요 취소 토글
    @PostMapping("/teacher")
    public String likeTeacher(@AuthenticationPrincipal AuthDetails authDetails,
                              @RequestParam("teacherId") Long teacherId) {
        Long userId = authDetails.getLogInDTO().getUserId();

        if (likeService.hasLiked(userId, teacherId)) {
            likeService.unlike(userId, teacherId); // 이미 좋아요 했으면 취소
        } else {
            likeService.like(new LikeDTO(userId, teacherId)); // 좋아요 등록
        }

        return "redirect:/like/teacher-detail/" + teacherId; // 다시 상세 페이지로 리다이렉트
    }


    // 선생님 좋아요 top5 조회
    @GetMapping("/test")
    public ModelAndView mainPage(ModelAndView model) {
        List<TeacherLikeRankingDto> topTeachers = likeService.getTop5TeachersByLikes();
        System.out.println("Top Teachers: " + topTeachers);  // 데이터 확인
        model.addObject("topTeachers", topTeachers);
        model.setViewName("main");
        return model;
    }
}
