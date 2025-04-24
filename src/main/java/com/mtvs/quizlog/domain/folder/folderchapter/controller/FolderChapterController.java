package com.mtvs.quizlog.domain.folder.folderchapter.controller;


import com.mtvs.quizlog.domain.folder.folderchapter.dto.FolderChapterDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/folder-chapters")
@Validated
public class FolderChapterController {

    private static final Logger logger = Logger.getLogger(FolderChapterController.class.getName());

    //FolderChapterService를 Controller에서 사용하기 위한 의존성 주입
    private final FolderChapterService folderChapterService;

    @Autowired
    public FolderChapterController(FolderChapterService folderChapterService) {
        this.folderChapterService = folderChapterService;
    }

    // 폴더생성
    @PostMapping("/create-folder-chapter")
    // ResponseEntity -> 클라이언트(브라우저, Postman 등)에 응답을 보낼 때, 직접 설정해서 보내고 싶을 때 사용하는 클래스
    public String createFolderChapter(@ModelAttribute FolderChapterDTO folderChapterDTO, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "folder-create-failed";
        } //유효성 검사 통과 후

        logger.info("post : /folderChapter " + folderChapterDTO.getTitle());

        // FolderChapterService에 게시판을 생성하는 메서드에 DTO를 전달한뒤 saveFolderChapter로 받음
        FolderChapterDTO savedFolderChapter = folderChapterService.createFolderChapter(folderChapterDTO);


        // 나중에 exaption이나 alret창으로 처리
        if (savedFolderChapter == null) {
            return "folder-create-failed";
        }else{
            return "redirect:/folder-chapters/folder";
            // 생성한 뒤 folder 페이지 다시 로드 (나중에 다른페이지로 넘어가거나 alert창 띄우기)
        }
    }

    // 전체 조회
    @GetMapping("/folder")
    public String getAllFolderChapters(Model model) {
        //폴더의 리스트 들을 model객체로 folder 페이지에 넘김
        List<FolderChapterDTO> folderChapters = folderChapterService.getAllFolderChapters();
        model.addAttribute("folderChapters", folderChapters);
        return "folder";
    }


    // 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warning("Validation error: {}"+ ex.getMessage());
        return ResponseEntity.badRequest().body(ex.getMessage());
        // badRequest: HTTP 상태 코드 400, body(ex.getMessage()): 예외(ex)의 메시지를 응답 본문(body)으로 설정
    }



}
