package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.user.dto.request.*;
import com.mtvs.quizlog.domain.user.dto.response.*;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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


    @GetMapping("/my-page")
    public void myPage() { }

    // 닉네임 수정
    @PatchMapping("/update-nickname")
    public String updateNickname(@AuthenticationPrincipal AuthDetails userDetails,
                                 @Validated UpdateNicknameRequestDTO updateNicknameRequestDTO,
                                 Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateNickname : {}", userId);

        UpdateNicknameResponseDTO updateNickname = userService.updateNickname(userId, updateNicknameRequestDTO);
        model.addAttribute("updatedNickname", updateNickname);

        return "/user/my-page";
    }

    // 이메일 수정
    @PatchMapping("/update-email")
    public String updateEmail(@AuthenticationPrincipal AuthDetails userDetails,
                              @Validated UpdateEmailRequestDTO updateEmailRequestDTO,
                              Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateEmail : {}", userId);

        UpdateEmailResponseDTO updateEmail = userService.updateEmail(userId, updateEmailRequestDTO);
        model.addAttribute("updatedEmail", updateEmail);

        return "/user/my-page";
    }

    // 역할 수정
    @PatchMapping("/update-role")
    public String updateRole(@AuthenticationPrincipal AuthDetails userDetails,
                             @Validated UpdateRoleRequestDTO updateRoleRequestDTO,
                             Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateRole : {}", userId);

        UpdateRoleResponseDTO updateRole = userService.updateRole(userId, updateRoleRequestDTO);
        model.addAttribute("updatedRole", updateRole);

        return "/user/my-page";
    }

    // 비밀번호 수정
    @PatchMapping("/update-password")
    public String updatePassword(@AuthenticationPrincipal AuthDetails userDetails,
                                 @Validated UpdatePasswordRequestDTO updatePasswordRequestDTO,
                                 Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updatePassword : {}", userId);

        UpdatePasswordResponseDTO updatePassword = userService.updatePassword(userId, updatePasswordRequestDTO);
        model.addAttribute("updatedPassword", updatePassword);

        return "/user/my-page";
    }

    // 회원 탈퇴 (Status를 ACTIVE에서 DELETED로 변경)
    @PatchMapping("/delete")
    public String deleteUser(@AuthenticationPrincipal AuthDetails userDetails,
                             @Validated DeleteUserRequestDTO deleteUserRequestDTO,
                             Model model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("deleteUser : {}", userId);

        DeleteUserResponseDTO deleteUser = userService.deleteUser(userId, deleteUserRequestDTO);
        model.addAttribute("deletedUser", deleteUser);

        return "redirect:/";
    }
}