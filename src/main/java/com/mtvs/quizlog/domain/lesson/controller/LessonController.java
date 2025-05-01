package com.mtvs.quizlog.domain.lesson.controller;

import com.mtvs.quizlog.domain.chapter.controller.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.UserChapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.lesson.dto.LessonDTO;
import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import com.mtvs.quizlog.domain.lesson.service.LessonService;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.mtvs.quizlog.domain.auth.model.AuthDetails;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Slf4j
public class LessonController {

    private final LessonService lessonService;
    private final UserService userService;
    private final ChapterService chapterService;

    /** 🔍 레슨 목록 */
    @GetMapping("/list")
    public String list(Model model) {
        List<Lesson> lessons = lessonService.findAllLessons();
        model.addAttribute("lessons", lessons);
        return "lesson/list";
    }

    /** 🔍 단일 레슨 */
    @GetMapping("/{lessonId}")
    public String detail(@AuthenticationPrincipal AuthDetails userDetails ,@PathVariable Long lessonId, Model model) {
        Lesson lesson = lessonService.findLessonById(lessonId);
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        model.addAttribute("userId",userDetails.getLogInDTO().getUserId());
        model.addAttribute("lesson", lesson);
        List<ChapterDto> chapterLists =chapterService.findChapter(user);
        for (ChapterDto chapterDto : chapterLists) {
            log.info("chapterLists: {}", chapterDto);
        }

        model.addAttribute("chapterLists", chapterLists);
        return "lesson/detail";
    }

    /** 📝 레슨 생성 폼 */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("lessonDTO", new LessonDTO());
        return "lesson/create";
    }

    /** ✅ 레슨 생성 처리 */
    @PostMapping("/create")
    public String create(@AuthenticationPrincipal AuthDetails userDetails,
                         @ModelAttribute @Validated LessonDTO lessonDTO) {
        User user = userService.findUser(userDetails.getLogInDTO().getUserId());
        lessonService.createLesson(lessonDTO, user);
        return "redirect:/lesson/list";
    }

    /** 📝 레슨 수정 폼 */
    @GetMapping("/edit/{lessonId}")
    public String editForm(@PathVariable Long lessonId, Model model) {
        Lesson lesson = lessonService.findLessonById(lessonId);
        model.addAttribute("lesson", lesson);
        return "lesson/edit";
    }

    /** ✅ 레슨 수정 처리 */
    @PostMapping("/edit/{lessonId}")
    public String edit(@PathVariable Long lessonId,
                       @ModelAttribute Lesson lesson) {
        lessonService.updateLesson(lessonId, lesson);
        return "redirect:/lesson/" + lessonId;
    }

    /** 🗑️ 레슨 삭제 */
    @PostMapping("/delete/{lessonId}")
    public String delete(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return "redirect:/lesson/list";
    }

    /** ➕ 챕터 추가 */
    @PostMapping("/{lessonId}/addChapter/{chapterId}")
    public String addChapter(@PathVariable Long lessonId,
                             @PathVariable Long chapterId) {
        lessonService.addChapterToLesson(lessonId, chapterId);
        return "redirect:/lesson/" + lessonId;
    }

    /** ➖ 챕터 제거 */
    @PostMapping("/{lessonId}/removeChapter/{chapterId}")
    public String removeChapter(@PathVariable Long lessonId,
                                @PathVariable Long chapterId) {
        lessonService.deleteChapterToLesson(lessonId, chapterId);
        return "redirect:/lesson/" + lessonId;
    }


}
