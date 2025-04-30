package com.mtvs.quizlog.domain.auth.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.UserChapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto;
import com.mtvs.quizlog.domain.like.service.LikeService;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    private final LikeService likeService;
    private final UserService userService;
    private final ChapterService chapterService;

    public IndexController(LikeService likeService, UserService userService, ChapterService chapterService) {
        this.likeService = likeService;
        this.userService = userService;
        this.chapterService = chapterService;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    // 선생님 좋아요 top5 조회
    @GetMapping("/main")
    public ModelAndView mainPage(@AuthenticationPrincipal AuthDetails userDetails, ModelAndView model) {
        // 로그인하지 않은 경우 예외 처리
        if (userDetails == null) {
            // 예외 처리 또는 로그인 페이지로 리다이렉트
            return new ModelAndView("redirect:/auth/login");
        }

        // 로그인한 사용자 정보 가져오기
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        // 인기 선생님 랭킹
        List<TeacherLikeRankingDto> topTeachers = likeService.getTop7TeachersByLikes();
        model.addObject("topTeachers", topTeachers);

        // 사용자 닉네임 전달
        model.addObject("nickname", user.getNickname());

        // 사용자 챕터 리스트 전달
        List<ChapterDto> chapterList = chapterService.findChapter(user);
        model.addObject("chapterList", chapterList);

        List<UserChapter> latestQuizzes = chapterService.findAll(); // 서비스 구현 필요
        model.addObject("latestQuizzes", latestQuizzes);

        model.setViewName("main");
        return model;
    }
}
