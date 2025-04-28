package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 유저 조회
    @Transactional
    public Page<UserListDTO> getUsers(String keyword, int page) {
        // PageRequest.of(page, size, sort)를 이용해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "userId"));

        Page<User> users;
        if (keyword == null || keyword.trim().isEmpty()) {
            // 만약 검색어가 없거나 공백이면 Status.ACTIVE (활성화된) 회원을 전부 가져옴
            users = userRepository.findByStatus(Status.ACTIVE, pageable);
        } else {
            // 만약 검색어가 있으면 Status.ACTIVE면서 닉네임에 검색어가 포함된 회원만 가져옴.
            users = userRepository.findByStatusAndNicknameContainingIgnoreCase(Status.ACTIVE, keyword, pageable);
        }

        // User를 UserListDTO로 변환해서 반환
        return users.map(user -> new UserListDTO(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        ));
    }

    // 탈퇴된 유저 조회
    @Transactional
    public Page<UserListDTO> getDeletedUsers(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.ASC, "userId"));

        Page<User> users;
        if (keyword == null || keyword.trim().isEmpty()) {
            users = userRepository.findByStatus(Status.DELETED, pageable);
        } else {
            users = userRepository.findByStatusAndNicknameContainingIgnoreCase(Status.DELETED, keyword, pageable);
        }

        // User를 UserListDTO로 변환해서 반환
        return users.map(user -> new UserListDTO(
                user.getUserId(),
                user.getNickname(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt()
        ));
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
