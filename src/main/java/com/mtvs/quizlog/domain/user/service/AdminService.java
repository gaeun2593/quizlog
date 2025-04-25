package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 유저 조회
    @Transactional
    public List<UserListDTO> getUsers() {
        List<UserListDTO> users = userRepository.findAllUsersForAdmin();
        return users;
    }

    // 탈퇴된 유저만 조회
    @Transactional
    public List<UserListDTO> getDeletedUsers() {
        List<UserListDTO> users = userRepository.findDeletedlUsersForAdmin();
        return users;
    }

    // 회원 계정 복구
    @Transactional
    public void restoreUserStatus(Long userId) {
        log.info("restoreUserStatus: {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        if (user.getStatus() != Status.DELETED) {
            throw new IllegalStateException("이미 활성 상태이거나 복구가 불가능한 상태입니다.");
        }

        user.updateStatus(Status.ACTIVE);
    }
}
