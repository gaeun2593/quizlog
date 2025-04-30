package com.mtvs.quizlog.solvedQuiz.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;
import com.mtvs.quizlog.domain.user.dto.LogInDTO;
import com.mtvs.quizlog.solvedQuiz.service.CheckedQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class CheckedQuizController {

    private final CheckedQuizService checkedQuizService  ;


    @PostMapping("/solvedQuiz")
    public String recentChapters(@RequestBody QuizForm quizform , @AuthenticationPrincipal AuthDetails userDetails ) {
        Long userId = userDetails.getLogInDTO().getUserId();
        checkedQuizService.saveCheckedQuiz(quizform, userId);
        return "recentChapters";
    }

}
