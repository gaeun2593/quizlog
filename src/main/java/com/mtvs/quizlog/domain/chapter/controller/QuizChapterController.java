package com.mtvs.quizlog.domain.chapter.controller;



import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizDto;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.QuizForm;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.RequestCreateChapterDTO;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.UserChapter;
import com.mtvs.quizlog.domain.chapter.controller.dto.request.*;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.folder.folderbookmarks.dto.FolderBookmarkDTO;
import com.mtvs.quizlog.domain.folder.folderbookmarks.service.FolderBookmarkService;
import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.service.QuizService;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import com.mtvs.quizlog.solvedQuiz.dto.UserCheckedQuizDTO;
import com.mtvs.quizlog.solvedQuiz.service.CheckedQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;



@Controller
@RequestMapping("/main")
@Slf4j
@RequiredArgsConstructor
public class QuizChapterController {

    private final ChapterService chapterService;
    private final QuizService quizService;
    private final UserService userService;
    private final FolderChapterService folderChapterService;
    private final FolderBookmarkService folderBookmarkService;
    private final CheckedQuizService checkedQuizService;

    @GetMapping("/create-chap")
    public String chapterView(Model model) {

        RequestCreateChapterDTO dto = new RequestCreateChapterDTO();

        dto.getQuizForm().add(new QuizForm());
        dto.getQuizForm().add(new QuizForm());

        model.addAttribute("requestCreateChapterDTO", dto);

        return "chapter/create-chap";
    }

    @PostMapping("/create-chap")
    public String createPost(@AuthenticationPrincipal AuthDetails userDetails, @Validated @ModelAttribute("requestCreateChapterDTO") RequestCreateChapterDTO requestCreateChapterDTO) {
        Long userId = userDetails.getLogInDTO().getUserId();

        User user = userService.findUser(userId);

        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getTitle());
        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getDescription());
        log.info("requestCreateChapterDTO:{}", requestCreateChapterDTO.getQuizForm().get(0).getWord());

        Chapter chapter = chapterService.createChapter(requestCreateChapterDTO , user);
        requestCreateChapterDTO.getQuizForm().forEach(quizForm -> quizService.createQuiz(user , quizForm, chapter));

        return "redirect:/main";
    }
   // /main/chapterList
    @GetMapping("/chapterList")
    public String quizList(@AuthenticationPrincipal AuthDetails userDetails ,Model model) {

        Long userId = userDetails.getLogInDTO().getUserId();

        User user = userService.findUser(userId);

        List<ChapterDto> chapter = chapterService.findChapter(user);

        model.addAttribute("chapterList", chapter);
        return "chapter/chapterList";
    }

    
   //  /main/chapterList 에서 이동
    @GetMapping("/chapters/{chapterId}")
    public String chapterView(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long chapterId, Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        List<QuizDto> quizDto = quizService.findbyQuizes(userId, chapterId);
        model.addAttribute("chapterId" , chapterId);
        model.addAttribute("quizList", quizDto);
        return "quiz/quizList";
    }

    @GetMapping("/{chapterId}/edit")
    public String edit(@AuthenticationPrincipal AuthDetails userDetails , @PathVariable Long chapterId , Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        RequestCreateChapterDTO dto = new RequestCreateChapterDTO();

        List<Chapter> chapter = chapterService.findchpaterAndQuizs(chapterId, userId);

        for (Chapter c : chapter) {
            dto.setTitle(c.getTitle());
            dto.setChapterId(c.getId());
            dto.setDescription(c.getDescription());
            List<Quiz> quizzes = c.getQuizzes();
            for (Quiz quiz : quizzes) {
                dto.getQuizForm().add(new QuizForm(quiz.getId(), quiz.getTitle(), quiz.getAnswer()));
            }

        }

        model.addAttribute("requestCreateChapterDTO", dto);
        return "chapter/editForm";
    }

    @PostMapping("/{chapterId}/edit")
    public String editPost(@PathVariable Long chapterId ,@Validated @ModelAttribute("requestCreateChapterDTO") RequestCreateChapterDTO requestCreateChapterDTO) {


        chapterService.updateChapter(chapterId , requestCreateChapterDTO.getTitle() , requestCreateChapterDTO.getDescription()) ;
        quizService.updateQuiz(chapterId ,requestCreateChapterDTO.getQuizForm());


        return "redirect:/main";
    }

    /* 최신순으로 모든 유저의 챕터조회 /main/recentChapters */
    @GetMapping("/recentChapters")
    public String recentChapters(Model model) {
        List<UserChapter> UserChapters = chapterService.findAll();
        model.addAttribute("userChapter", UserChapters);

        return "chapter/recentChapters";
    }


    /* 챕터의 상세페이지 */
    @GetMapping("/recentChapters/{chapterId}")
    public String recentChapter(@PathVariable Long chapterId , Model model, @AuthenticationPrincipal AuthDetails userDetails) {
        Long userId = userDetails.getLogInDTO().getUserId();
        List<QuizForm> checkdQuizs = checkedQuizService.findCheckdQuizs(chapterId, userId);

        List<QuizForm> quizSet = quizService.findQuiz(chapterId);

        if(checkdQuizs.size() == quizSet.size()) {
            UserCheckedQuizDTO checkdQuiz = checkedQuizService.findCheckdQuiz(chapterId, userId);
            model.addAttribute("checkdQuiz", checkdQuiz);
            return "quiz/QuizCompletionRate" ;
        }

        else if(checkdQuizs.size() > 0) {
            QuizForm quiz = checkdQuizs.get(checkdQuizs.size() - 1);
            log.info("quiz = {}", quiz);
            List<QuizForm> newQuizSet = new ArrayList<>();
            for(int i = 0 ; i < quizSet.size() ; i++) {
                if(quizSet.get(i).equals(quiz)) {
                    newQuizSet = Arrays.asList(quizSet.subList(i+1, quizSet.size()).toArray(new QuizForm[0]));
                    break;
                }
            }
            log.info("newQuizSet = {}", newQuizSet);
            model.addAttribute("checkdQuizs" , checkdQuizs) ;
            model.addAttribute("quizSet" ,newQuizSet) ;
        }

        else {
            model.addAttribute("quizSet" ,quizSet) ;

        }

        model.addAttribute("chapterId" , chapterId) ;
       // model.addAttribute("title", title);

        log.info("quizSet = {}", quizSet);



        return "quiz/solvedForm" ;
    }

    @GetMapping("/search") // 검색 요청
    public String search(@RequestParam("search") String search, Model model) {
        log.info("search = {}", search);
        List<ChapterDto> chapterDto  = chapterService.findTitle(search);
        model.addAttribute("chapterDto", chapterDto);
        return "chapter/searchPage" ;
    }
    
    // recentQuizList로 이동 (퀴즈 카드로 넘어가는 곳(즐겨찾기, 챕터 즐겨찾기 가능 페이지))

    @GetMapping("/chapters/{chapterId}/{title}") // 퀴즈 진도 페이지 요청
    public String solvedQuiz(@AuthenticationPrincipal AuthDetails userDetails,@PathVariable Long chapterId , Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        List<QuizForm> checkdQuizs = checkedQuizService.findCheckdQuizs(chapterId, userId);

        List<QuizForm> quizSet = quizService.findQuiz(chapterId);

        if(checkdQuizs.size() == quizSet.size()) {
            UserCheckedQuizDTO checkdQuiz = checkedQuizService.findCheckdQuiz(chapterId, userId);
            model.addAttribute("checkdQuiz", checkdQuiz);
            return "quiz/QuizCompletionRate" ;
        }

        else if(checkdQuizs.size() > 0) {
            QuizForm quiz = checkdQuizs.get(checkdQuizs.size() - 1);
            log.info("quiz = {}", quiz);
            List<QuizForm> newQuizSet = new ArrayList<>();
            for(int i = 0 ; i < quizSet.size() ; i++) {
                if(quizSet.get(i).equals(quiz)) {
                    newQuizSet = Arrays.asList(quizSet.subList(i+1, quizSet.size()).toArray(new QuizForm[0]));
                    break;
                }
            }
            log.info("newQuizSet = {}", newQuizSet);

            model.addAttribute("checkdQuizs" , checkdQuizs) ;
            model.addAttribute("quizSet" ,newQuizSet) ;
        }

        else {
            model.addAttribute("quizSet" ,quizSet) ;

        }


        List<QuizForm> quizForm  = quizService.findAll(chapterId);
        model.addAttribute("quizForm", quizForm);

        /* 챕터 Id로 객체찾아서 퀴즈 페이지로 전달 */
        Chapter chapter = chapterService.findId(chapterId);
        model.addAttribute("chapter", chapter);

        // 로그인한 유저객체 가져와서
        User user = userService.findUser(userId);

        // 유저의 챕터폴더 가져옴
        List<FolderChapterDTO> folderChapters = folderChapterService.getAllFolderChapters(user);
        model.addAttribute("folderChapters", folderChapters);

        // 유저의 퀴즈폴더 가져옴
        List<FolderBookmarkDTO> folderBookmarks = folderBookmarkService.getAllfolderBookmarks(user);
        model.addAttribute("folderBookmarks", folderBookmarks);


        model.addAttribute("chapterId" , chapterId) ;
        return "quiz/recentQuizList" ;
    }

    @GetMapping("/{chapterId}/check")
    public String checkedQuiz(@PathVariable long chapterId , @AuthenticationPrincipal AuthDetails userDetails ,  Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        UserCheckedQuizDTO checkdQuiz = checkedQuizService.findCheckdQuiz(chapterId, userId);
        model.addAttribute("chapterId" , chapterId) ;
        model.addAttribute("checkdQuiz", checkdQuiz);
        return "quiz/QuizCompletionRate" ;
    }

    @GetMapping("/{chapterId}/resolve")
    public String reschekcQuiz(@PathVariable long chapterId , @AuthenticationPrincipal AuthDetails userDetails , Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        checkedQuizService.removeCheckdQuiz(chapterId, userId);
        Chapter chapter = chapterService.findId(chapterId);

        String title = chapter.getTitle();
        return "redirect:/main/chapters/" + chapterId + "/" + title;
    }


}

