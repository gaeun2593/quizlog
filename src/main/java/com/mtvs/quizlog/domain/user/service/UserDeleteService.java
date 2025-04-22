package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.request.DeleteUserRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.DeleteUserResponseDTO;
import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserDeleteService {
    private static final Logger log = LoggerFactory.getLogger(UserCreateService.class);
    private UserRepository userRepository;

    public UserDeleteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원 탈퇴
    @Transactional
    public DeleteUserResponseDTO deleteUser(Long userId, DeleteUserRequestDTO deleteUserRequestDTO) {
        log.info("deleteUser: {}", deleteUserRequestDTO.getStatus());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 사용자 상태 확인
        Optional<User> findUserStatus = userRepository.findById(userId);
        if (findUserStatus.get().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("이미 탈퇴된 회원입니다.");
        }

        try {
            User deletedUser = user.toBuilder()
                    .status(Status.DELETED)
                    .deletedAt(LocalDate.now())
                    .build();

            User savedUser = userRepository.save(deletedUser);
            log.info("saved user: {}", savedUser);

            return new DeleteUserResponseDTO(savedUser.getStatus(), savedUser.getDeletedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
