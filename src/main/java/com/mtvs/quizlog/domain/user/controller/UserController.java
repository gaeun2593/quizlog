package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.user.dto.request.*;
import com.mtvs.quizlog.domain.user.dto.response.*;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
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
    @GetMapping("/sign-up")
    public void signUp() { }

    @PostMapping("/sign-up")
    public String createUser(SignUpRequestDTO signUpRequestDTO, Model model) {
        log.info("createUser: {}", signUpRequestDTO.getNickname());

        SignUpResponseDTO savedUser = userService.createUser(signUpRequestDTO);

        model.addAttribute("savedUser", savedUser);

        return "redirect:/auth/login";
    }

    // 닉네임 수정
    @PatchMapping("/update-nickname/{userId}")
    public ResponseEntity<UpdateNicknameResponseDTO> updateNickname(@PathVariable("userId") Long userId,
                                                                    @Validated @RequestBody UpdateNicknameRequestDTO updateNicknameRequestDTO) {
        log.info("updateNickname : {}", userId);

        UpdateNicknameResponseDTO updateNickname = userService.updateNickname(userId, updateNicknameRequestDTO);

        if (updateNickname == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.ok().body(updateNickname);
        }
    }

    // 이메일 수정
    @PatchMapping("/update-email/{userId}")
    public ResponseEntity<UpdateEmailResponseDTO> updateEmail(@PathVariable("userId") Long userId,
                                                              @Validated @RequestBody UpdateEmailRequestDTO updateEmailRequestDTO) {
        log.info("updateEmail : {}", userId);

        UpdateEmailResponseDTO updateEmail = userService.updateEmail(userId, updateEmailRequestDTO);

        if (updateEmail == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.ok().body(updateEmail);
        }
    }

    // 역할 수정
    @PatchMapping("/update-role/{userId}")
    public ResponseEntity<UpdateRoleResponseDTO> updateRole(@PathVariable("userId") Long userId,
                                                            @Validated @RequestBody UpdateRoleRequestDTO updateRoleRequestDTO) {
        log.info("updateRole : {}", userId);

        UpdateRoleResponseDTO updateRole = userService.updateRole(userId, updateRoleRequestDTO);

        return ResponseEntity.ok().body(updateRole);
    }

    // 비밀번호 수정
    @PatchMapping("/update-password/{userId}")
    public ResponseEntity<UpdatePasswordResponseDTO> updatePassword(@PathVariable("userId") Long userId,
                                                                    @Validated @RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        log.info("updatePassword : {}", userId);

        UpdatePasswordResponseDTO updatePassword = userService.updatePassword(userId, updatePasswordRequestDTO);

        if (updatePassword == null) {
            return ResponseEntity.status(500).body(null);
        } else {
            return ResponseEntity.ok().body(updatePassword);
        }
    }

    // 회원 탈퇴 (Status를 ACTIVE에서 DELETED로 변경)
    @PatchMapping("/delete/{userId}")
    public ResponseEntity<DeleteUserResponseDTO> deleteUser(@PathVariable("userId") Long userId,
                                                            @Validated @RequestBody DeleteUserRequestDTO deleteUserRequestDTO) {
        log.info("deleteUser : {}", userId);

        DeleteUserResponseDTO deleteUser = userService.deleteUser(userId, deleteUserRequestDTO);

        return ResponseEntity.ok().body(deleteUser);
    }
}