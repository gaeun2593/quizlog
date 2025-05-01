package com.mtvs.quizlog.domain.folder.folderchapter.controller;

import com.mtvs.quizlog.domain.folder.folderbookmarks.dto.CreateFolderDTO;
import com.mtvs.quizlog.domain.folder.folderchapter.service.FolderChapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiFolderChapterController {

    private final FolderChapterService folderChapterService;


    @PostMapping("/createFolderChapter")
    public ResponseEntity createFolderChapter(@RequestBody CreateFolderDTO createFolderDTO) {
        folderChapterService.save(createFolderDTO);
        log.info("createFolderChapter : " + createFolderDTO);
        return ResponseEntity.status(200).build();
    }
}
