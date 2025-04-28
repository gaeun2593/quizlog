package com.mtvs.quizlog.domain.folder.folderbookmarks.controller;


import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.folder.folderbookmarks.dto.FolderBookmarkDTO;
import com.mtvs.quizlog.domain.folder.folderbookmarks.service.FolderBookmarkService;
import com.mtvs.quizlog.domain.folder.folderchapter.controller.FolderChapterController;
import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/folder-bookmarks")
public class FolderBookmarkController {

    private static final Logger logger = Logger.getLogger(FolderBookmarkController.class.getName());
    private final UserService userService;

    //FolderBookmarkService를 Controller에서 사용하기 위한 의존성 주입
    private final FolderBookmarkService folderBookmarkService;

    @Autowired
    public FolderBookmarkController(FolderBookmarkService folderBookmarkService, UserService userService) {
        this.folderBookmarkService = folderBookmarkService;
        this.userService = userService;
    }

    // 폴더생성
    @PostMapping("/create-folder-bookmark")
    // ResponseEntity -> 클라이언트(브라우저, Postman 등)에 응답을 보낼 때, 직접 설정해서 보내고 싶을 때 사용하는 클래스
    public String createFolderBookmark(@ModelAttribute FolderBookmarkDTO folderBookmarkDTO, @AuthenticationPrincipal AuthDetails userDetails) {
        logger.info("post : /folderBookmark" + folderBookmarkDTO.getTitle());

        // 로그인한 유저의 userId로 User 객체를 가져옴
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);
        // FolderChapterService에 게시판을 생성하는 메서드에 DTO를 전달한뒤 saveFolderChapter로 받음
        folderBookmarkService.createFolderBookmark(folderBookmarkDTO,user);

        return "redirect:/folder-bookmarks/folder-bookmarks-view";

    }

    // 폴더명 수정
    @PostMapping("/update-folder-bookmark")
    public String updateFolderBookmark(@RequestParam("folderUpdateTitle") String folderUpdateTitle, @RequestParam("folderTitle") String folderTitle, @AuthenticationPrincipal AuthDetails userDetails) {
        logger.info("patch : /folderUpdateTitle " +folderUpdateTitle);

        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        // updateFolderChapte로 넘김
        folderBookmarkService.updateFolderBookmark(folderUpdateTitle, folderTitle, user);

        return "redirect:/folder-bookmarks/folder-bookmarks-view";
    }


    // 전체 조회
    @GetMapping("/folder-bookmarks-view")
    public String getAllFolderBookmarks(@AuthenticationPrincipal AuthDetails userDetails, Model model) {
        //폴더의 리스트 들을 model객체로 folder 페이지에 넘김

        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        List<FolderBookmarkDTO> folderBookmarks = folderBookmarkService.getAllfolderBookmarks(user);
        model.addAttribute("folderBookmarks", folderBookmarks);
        return "folder/folder-bookmarks";
    }

    //삭제
    @PostMapping("/delete-folder-bookmark")
    public String deleteFolderBookmark(@RequestParam("folderTitle") String folderTitle, @AuthenticationPrincipal AuthDetails userDetails) {
        logger.info("DELETE /api/folderChapter/{}"+ folderTitle);

        // 로그인한 유저객체 가져와서
        Long userId = userDetails.getLogInDTO().getUserId();
        User user = userService.findUser(userId);

        folderBookmarkService.deleteFolderBookmark(folderTitle, user);
        return "redirect:/folder-bookmarks/folder-bookmarks-view";
    }


}
