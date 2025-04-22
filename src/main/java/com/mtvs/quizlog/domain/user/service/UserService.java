package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.request.*;
import com.mtvs.quizlog.domain.user.dto.response.*;
import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 회원가입
    @Transactional
    public SignUpResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {
        log.info("createUser: {}", signUpRequestDTO.getNickname());

        // 닉네임 중복 검사
        Optional<User> findUserNickname = userRepository.findByNickname(signUpRequestDTO.getNickname());
        if (findUserNickname.isPresent()) {
            throw new IllegalArgumentException("nickname already exists");
        }

        // 이메일 중복 검사
        Optional<User> findUserEmail = userRepository.findByEmail(signUpRequestDTO.getEmail());
        if (findUserEmail.isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }

        // 비밀번호 확인 일치 여부 검사
        if (!signUpRequestDTO.getPassword().equals(signUpRequestDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("password does not match");
        }

        try {
            User user = User.builder()
                    .nickname(signUpRequestDTO.getNickname())
                    .email(signUpRequestDTO.getEmail())
                    .password(bCryptPasswordEncoder.encode(signUpRequestDTO.getPassword()))
                    .role(signUpRequestDTO.getRole())
                    .status(Status.ACTIVE)
                    .createdAt(LocalDate.now())
                    .build();

            User savedUser = userRepository.save(user);
            log.info("saved user: {}", savedUser);

            return new SignUpResponseDTO(savedUser.getNickname(), savedUser.getEmail(), savedUser.getPassword(),
                    savedUser.getRole(), savedUser.getStatus(), savedUser.getCreatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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
                    .updatedAt(LocalDate.now())
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
                    .updatedAt(LocalDate.now())
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
                    .updatedAt(LocalDate.now())
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
                    .updatedAt(LocalDate.now())
                    .build();

            User savedUser = userRepository.save(updatedUser);
            log.info("saved user: {}", savedUser);

            return new UpdatePasswordResponseDTO(savedUser.getPassword(), savedUser.getUpdatedAt());
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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

    // 로그인

}
