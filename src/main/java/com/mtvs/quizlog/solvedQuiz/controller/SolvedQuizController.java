package com.mtvs.quizlog.solvedQuiz.controller;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class SolvedQuizController {

    private final QuizService quizService;

    private final ChapterService chapterService;

    @GetMapping("/recentChapters")
    public String recentChapters(Model model) {
        return "recentChapters";
    }

}
