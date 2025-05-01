package com.mtvs.quizlog.domain.compare.service;

import com.mtvs.quizlog.domain.compare.DTO.CompareUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiCompareController {

    @PostMapping("/api/compare")
    public ResponseEntity add(@RequestBody CompareUser compareUser) {
        return ResponseEntity.ok().build();
    }
}
