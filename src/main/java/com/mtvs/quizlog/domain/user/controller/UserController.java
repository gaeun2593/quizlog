package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.auth.model.AuthDetails;
import com.mtvs.quizlog.domain.user.dto.request.*;
import com.mtvs.quizlog.domain.user.dto.response.*;
import com.mtvs.quizlog.domain.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView createUser(SignUpRequestDTO signUpRequestDTO, ModelAndView model) {
        log.info("createUser: {}", signUpRequestDTO.getNickname());

        SignUpResponseDTO savedUser = userService.createUser(signUpRequestDTO);
        model.addObject("savedUser", savedUser);
        model.setViewName("redirect:/auth/login");

        return model;
    }


    @GetMapping("/my-page")
    public void myPage() { }

    // 닉네임 수정
    @PatchMapping("/update-nickname")
    public ModelAndView updateNickname(@AuthenticationPrincipal AuthDetails userDetails,
                                 @Validated UpdateNicknameRequestDTO updateNicknameRequestDTO,
                                 ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateNickname : {}", userId);

        UpdateNicknameResponseDTO updateNickname = userService.updateNickname(userId, updateNicknameRequestDTO);
        model.addObject("updatedNickname", updateNickname);
        model.setViewName("redirect:/user/my-page");

        return model;
    }

    // 이메일 수정
    @PatchMapping("/update-email")
    public ModelAndView updateEmail(@AuthenticationPrincipal AuthDetails userDetails,
                              @Validated UpdateEmailRequestDTO updateEmailRequestDTO,
                                    ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateEmail : {}", userId);

        UpdateEmailResponseDTO updateEmail = userService.updateEmail(userId, updateEmailRequestDTO);
        model.addObject("updatedEmail", updateEmail);
        model.setViewName("redirect:/user/my-page");

        return model;
    }

    // 역할 수정
    @PatchMapping("/update-role")
    public ModelAndView updateRole(@AuthenticationPrincipal AuthDetails userDetails,
                                   @Validated UpdateRoleRequestDTO updateRoleRequestDTO,
                                   ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updateRole : {}", userId);

        UpdateRoleResponseDTO updateRole = userService.updateRole(userId, updateRoleRequestDTO);
        model.addObject("updatedRole", updateRole);
        model.setViewName("redirect:/user/my-page");

        return model;
    }

    // 비밀번호 수정
    @PatchMapping("/update-password")
    public ModelAndView updatePassword(@AuthenticationPrincipal AuthDetails userDetails,
                                       @Validated UpdatePasswordRequestDTO updatePasswordRequestDTO,
                                       ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("updatePassword : {}", userId);

        UpdatePasswordResponseDTO updatePassword = userService.updatePassword(userId, updatePasswordRequestDTO);
        model.addObject("updatedPassword", updatePassword);
        model.setViewName("redirect:/user/my-page");

        return model;
    }

    // 회원 탈퇴 (Status를 ACTIVE에서 DELETED로 변경)
    @PatchMapping("/delete")
    public ModelAndView deleteUser(@AuthenticationPrincipal AuthDetails userDetails,
                             @Validated DeleteUserRequestDTO deleteUserRequestDTO,
                                   ModelAndView model) {
        Long userId = userDetails.getLogInDTO().getUserId();
        log.info("deleteUser : {}", userId);

        DeleteUserResponseDTO deleteUser = userService.deleteUser(userId, deleteUserRequestDTO);
        model.addObject("deletedUser", deleteUser);
        model.setViewName("redirect:/");

        return model;
    }

    @GetMapping("/compare")
    public String compare() {
        return "compare";
    }
}