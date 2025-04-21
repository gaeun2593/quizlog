package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.user.dto.request.SignUpRequestDTO;
import com.mtvs.quizlog.domain.user.dto.request.UpdateNicknameRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.SignUpResponseDTO;
import com.mtvs.quizlog.domain.user.dto.response.UpdateNicknameResponseDTO;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponseDTO> createUser(@Validated @RequestBody SignUpRequestDTO signUpRequestDTO) {
        log.info("createUser: {}", signUpRequestDTO.getNickname());

        SignUpResponseDTO savedUser = userService.createUser(signUpRequestDTO);

        return ResponseEntity.ok().body(savedUser);
    }

    // 로그인

    // 로그아웃

    // 닉네임 수정
    @PatchMapping("/update-nickname/{userId}")
    public ResponseEntity<UpdateNicknameResponseDTO> updateNickname(@PathVariable("userId") Long userId,
            @Validated @RequestBody UpdateNicknameRequestDTO updateNicknameRequestDTO) {
        log.info("petch : {}", userId);

        UpdateNicknameResponseDTO updateNickname = userService.updateNickname(userId, updateNicknameRequestDTO);

        if (updateNickname == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.ok().body(updateNickname);
        }
    }

    // 이메일 수정
    @PatchMapping("/update-email/{userId}")
    public ResponseEntity<UpdateNicknameResponseDTO> updateNickname(@PathVariable("userId") Long userId,
                                                                    @Validated @RequestBody UpdateNicknameRequestDTO updateNicknameRequestDTO) {
        log.info("petch : {}", userId);

        UpdateNicknameResponseDTO updateNickname = userService.updateNickname(userId, updateNicknameRequestDTO);

        if (updateNickname == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.ok().body(updateNickname);
        }
    }

    // 역할 수정
    // 비밀번호 수정
    // 회원 탈퇴

}
