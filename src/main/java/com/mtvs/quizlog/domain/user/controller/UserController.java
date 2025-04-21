package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.user.dto.request.SignUpRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.SignUpResponseDTO;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> createUser(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO) {
        log.info("createUser: {}", signUpRequestDTO.getNickname());

        SignUpResponseDTO savedUser = userService.createUser(signUpRequestDTO);

        return ResponseEntity.ok().body(savedUser);
    }
}
