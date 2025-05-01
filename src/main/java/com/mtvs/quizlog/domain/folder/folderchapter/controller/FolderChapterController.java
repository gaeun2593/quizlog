package com.mtvs.quizlog.domain.folder.folderchapter.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.service.ChapterService;
import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import com.mtvs.quizlog.domain.quiz.service.QuizService;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import com.mtvs.quizlog.solvedQuiz.dto.UserCheckedChapterDTO;
import com.mtvs.quizlog.solvedQuiz.service.CheckedQuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/folder-chapters")
@Validated
@Slf4j
@RequiredArgsConstructor
public class FolderChapterController {


    private final UserService userService;

    //FolderChapterService를 Controller에서 사용하기 위한 의존성 주입
    private final FolderChapterService folderChapterService;

    private final ChapterService chapterService;
    private final CheckedQuizService checkedQuizService;



    // 폴더생성(챕터 페이지에서 챕터를 담은 폴더 생성)
    @PostMapping("/create-folder-chapter")
    // ResponseEntity -> 클라이언트(브라우저, Postman 등)에 응답을 보낼 때, 직접 설정해서 보내고 싶을 때 사용하는 클래스
    public String createFolderChapter(@ModelAttribute FolderChapterDTO folderChapterDTO, @AuthenticationPrincipal AuthDetails userDetails, @RequestParam("chapterId") Long chapterId) {


        // 로그인한 유저의 userId로 User 객체를 가져옴
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);
        // FolderChapterService에 폴더를 생성하는 메서드에 DTO를 전달한뒤 saveFolderChapter로 받음
       folderChapterService.createFolderChapter(folderChapterDTO,user,chapterId);

        return "redirect:/folder-chapters/folder-chapters-view";
    }

    // 폴더생성2 (폴더 페이지에서 빈폴더 생성)
    @PostMapping("/create-folder-chapter2")
    // ResponseEntity -> 클라이언트(브라우저, Postman 등)에 응답을 보낼 때, 직접 설정해서 보내고 싶을 때 사용하는 클래스
    public String createFolderChapter(@ModelAttribute FolderChapterDTO folderChapterDTO,@AuthenticationPrincipal AuthDetails userDetails) {

        // 로그인한 유저의 userId로 User 객체를 가져옴
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);
        log.info("user : " + folderChapterDTO.getTitle());
        // FolderChapterService에 폴더를 생성하는 메서드에 DTO를 전달한뒤 saveFolderChapter로 받음
        folderChapterService.createFolderChapter2(folderChapterDTO,user);

        return "redirect:/folder-chapters/folder-chapters-view";
    }

    // 해당 챕터를 기존 폴더에 추가
    @PostMapping("/add-chapter-to-folder")
    public String addChapterToFolder(@RequestParam("folderChapterId") int folderChapterId, @AuthenticationPrincipal AuthDetails userDetails, @RequestParam("chapterId") Long chapterId){

        // 로그인한 유저의 userId로 User 객체를 가져옴
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        folderChapterService.addChapterToFolder(folderChapterId,chapterId,user);

        System.out.println("📌 폴더 제목(title): " + folderChapterId);
        System.out.println("📌 챕터 ID(chapterId): " + chapterId);
        System.out.println("📌 유저 ID(user): " + user);



        return String.format("redirect:/main/recentChapters/%d", chapterId);
    }

// fsf
    // 폴더명 수정
    @PostMapping("/update-folder-chapter")
    public String updateFolderChapter(@RequestParam("folderUpdateTitle") String folderUpdateTitle, @RequestParam("folderTitle") String folderTitle,@AuthenticationPrincipal AuthDetails userDetails) {


        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        // updateFolderChapte로 넘김
       folderChapterService.updateFolderChapter(folderUpdateTitle, folderTitle, user);

        return "redirect:/folder-chapters/folder-chapters-view";
    }

    // 전체 조회
    @GetMapping("/folder-chapters-view")
    public String getAllFolderChapters(@AuthenticationPrincipal AuthDetails userDetails, Model model) {
        //폴더의 리스트 들을 model객체로 folder 페이지에 넘김

        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        List<FolderChapterDTO> folderChapters = folderChapterService.getAllFolderChapters(user);
        model.addAttribute("folderChapters", folderChapters);

        return "folder/folder-chapters";
    }


    // 폴더 속 챕터 조회
    @PostMapping("/folder-chapter-detail")
    public String folderChapterDetail(@AuthenticationPrincipal AuthDetails userDetails, @RequestParam("folderChapterId") long folderChapterId , Model model) {
        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
       // log.info("folderChapterId ={} " , folderChapterId);
        //List<Chapter> chapters = chapterService.findChapterByFolderChapterId(userId, folderChapterId);
        List<UserCheckedChapterDTO> checkedChapters = checkedQuizService.findCheckedChapters(userId);
        List<UserCheckedChapterDTO> chekedFolder = checkedQuizService.findChekedFolder(folderChapterId);
        model.addAttribute("folderChapterId", folderChapterId);
        model.addAttribute("chekedFolder", chekedFolder);
        model.addAttribute("checkedChapters", checkedChapters);

        return "folder/folder-chapter-detail";
    }

    // 삭제
    @PostMapping("/delete-folder-chapter")
    public String deleteFolderChapter(@RequestParam("folderTitle") String folderTitle, @AuthenticationPrincipal AuthDetails userDetails) {


        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        folderChapterService.deleteFolderChapter(folderTitle, user);
        return "redirect:/folder-chapters/folder-chapters-view";
    }




    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
        // badRequest: HTTP 상태 코드 400, body(ex.getMessage()): 예외(ex)의 메시지를 응답 본문(body)으로 설정
    }



}
