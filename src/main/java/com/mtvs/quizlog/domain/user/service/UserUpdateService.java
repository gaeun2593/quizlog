package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.request.UpdateEmailRequestDTO;
import com.mtvs.quizlog.domain.user.dto.request.UpdateNicknameRequestDTO;
import com.mtvs.quizlog.domain.user.dto.request.UpdatePasswordRequestDTO;
import com.mtvs.quizlog.domain.user.dto.request.UpdateRoleRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.UpdateEmailResponseDTO;
import com.mtvs.quizlog.domain.user.dto.response.UpdateNicknameResponseDTO;
import com.mtvs.quizlog.domain.user.dto.response.UpdatePasswordResponseDTO;
import com.mtvs.quizlog.domain.user.dto.response.UpdateRoleResponseDTO;
import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserUpdateService {
    private static final Logger log = LoggerFactory.getLogger(UserCreateService.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserUpdateService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    // 닉네임 수정
    @Transactional
    public UpdateNicknameResponseDTO updateNickname(Long userId, UpdateNicknameRequestDTO updateNicknameRequestDTO) {
        log.info("updateNickname: {}", updateNicknameRequestDTO.getNickname());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 사용자 상태 확인
        Optional<User> findUserStatus = userRepository.findById(userId);
        if (findUserStatus.get().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("탈퇴된 회원입니다.");
        }

        // 닉네임 중복 검사
        Optional<User> findUserNickname = userRepository.findByNickname(updateNicknameRequestDTO.getNickname());
        if (findUserNickname.isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        try {
            User updatedUser = user.toBuilder()
                    .nickname(updateNicknameRequestDTO.getNickname())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(updatedUser);
            log.info("saved user: {}", savedUser);

            return new UpdateNicknameResponseDTO(savedUser.getNickname(), savedUser.getUpdatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // 이메일 수정
    @Transactional
    public UpdateEmailResponseDTO updateEmail(Long userId, UpdateEmailRequestDTO updateEmailRequestDTO) {
        log.info("updateEmail: {}", updateEmailRequestDTO.getEmail());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 사용자 상태 확인
        Optional<User> findUserStatus = userRepository.findById(userId);
        if (findUserStatus.get().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("탈퇴된 회원입니다.");
        }

        // 현재 사용 중인 이메일인지 검사
        if (user.getEmail().equals(updateEmailRequestDTO.getEmail())) {
            throw new IllegalArgumentException("현재 사용 중인 이메일입니다.");
        }

        try {
            User updatedUser = user.toBuilder()
                    .email(updateEmailRequestDTO.getEmail())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(updatedUser);
            log.info("saved user: {}", savedUser);

            return new UpdateEmailResponseDTO(savedUser.getEmail(), savedUser.getUpdatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // 역할 수정
    @Transactional
    public UpdateRoleResponseDTO updateRole(Long userId, UpdateRoleRequestDTO updateRoleRequestDTO) {
        log.info("updateRole: {}", updateRoleRequestDTO.getRole());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 사용자 상태 확인
        Optional<User> findUserStatus = userRepository.findById(userId);
        if (findUserStatus.get().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("탈퇴된 회원입니다.");
        }

        try {
            User updatedUser = user.toBuilder()
                    .role(updateRoleRequestDTO.getRole())
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(updatedUser);
            log.info("saved user: {}", savedUser);

            return new UpdateRoleResponseDTO(savedUser.getRole(), savedUser.getUpdatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // 비밀번호 수정
    @Transactional
    public UpdatePasswordResponseDTO updatePassword(Long userId, UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        log.info("updatePassword: {}", updatePasswordRequestDTO.getNewPassword());

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        // 사용자 상태 확인
        Optional<User> findUserStatus = userRepository.findById(userId);
        if (findUserStatus.get().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("탈퇴된 회원입니다.");
        }

        // 비밀번호 확인 일치 여부 검사
        if (!updatePasswordRequestDTO.getNewPassword().equals(updatePasswordRequestDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("password does not match");
        }

        try {
            User updatedUser = user.toBuilder()
                    .password(bCryptPasswordEncoder.encode(updatePasswordRequestDTO.getNewPassword()))
                    .updatedAt(LocalDateTime.now())
                    .build();

            User savedUser = userRepository.save(updatedUser);
            log.info("saved user: {}", savedUser);

            return new UpdatePasswordResponseDTO(savedUser.getPassword(), savedUser.getUpdatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
