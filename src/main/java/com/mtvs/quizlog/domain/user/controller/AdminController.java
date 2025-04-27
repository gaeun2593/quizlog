package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // 회원 목록 조회
    @GetMapping("/list-users")
    public ModelAndView listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            ModelAndView model) {
        log.info("List users");

        Page<UserListDTO> usersPage = adminService.getUsers(keyword, page);

        // usersPage에서 실제 회원 목록 데이터만 꺼내서(getContent()), 모델에 "users"라는 이름으로 담기
        model.addObject("users", usersPage.getContent());
        // 현재 페이지 번호도 모델에 추가
        model.addObject("currentPage", page);
        // 전체 페이지 수를 모델에 추가
        model.addObject("totalPages", usersPage.getTotalPages());
        // 검색어도 그대로 모델에 담기 (검색창에 다시 보여주거나, 검색 기능 유지하려기 위함)
        model.addObject("keyword", keyword);
        model.setViewName("/admin/manage");

        return model;
    }

    // 탈퇴된 회원 목록 조회
    @GetMapping("/list-deleted-users")
    public ModelAndView listDeletedUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            ModelAndView model) {
        log.info("List deleted users");

        Page<UserListDTO> deletedUsersPage = adminService.getDeletedUsers(keyword, page);

        model.addObject("deletedUsers", deletedUsersPage.getContent());
        model.addObject("currentPage", page);
        model.addObject("totalPages", deletedUsersPage.getTotalPages());
        model.addObject("keyword", keyword);
        model.setViewName("/admin/deleted");

        return model;
    }

    // 회원 계정 복구
    @PostMapping("/restore-user")
    public ModelAndView restoreUserStatus(@RequestParam Long userId, ModelAndView model) {
        log.info("Restore user");

        adminService.restoreUserStatus(userId);
        model.setViewName("redirect:/admin/list-deleted-users");

        return model;
    }

    // 문의사항 조회 - 채팅
}
