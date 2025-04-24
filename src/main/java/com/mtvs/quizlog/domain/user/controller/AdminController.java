package com.mtvs.quizlog.domain.user.controller;

import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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
    public ModelAndView listUsers(ModelAndView model) {
        log.info("List users");

        List<UserListDTO> users = adminService.getUsers();
        model.addObject("users", users);
        model.setViewName("/admin/manage");

        return model;
    }

    // 회원 탈퇴 조회
    @GetMapping("/list-deleted-users")
    public ModelAndView listDeletedUsers(ModelAndView model) {
        log.info("List deleted users");

        List<UserListDTO> deletedUsers = adminService.getDeletedUsers();
        model.addObject("deletedUsers", deletedUsers);
        model.setViewName("/admin/deleted");

        return model;
    }

    // 문의사항 조회 - 채팅으로
}
