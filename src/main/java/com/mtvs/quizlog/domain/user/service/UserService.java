package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.request.SignUpRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.SignUpResponseDTO;
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

        User user = User.builder()
                .nickname(signUpRequestDTO.getNickname())
                .email(signUpRequestDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(signUpRequestDTO.getPassword()))
                .role(signUpRequestDTO.getRole())
                .status(Status.ACTIVE)
                .createdAt(LocalDate.now())
                .build();

        User savedUser = userRepository.save(user);

        return new SignUpResponseDTO(savedUser.getNickname(), savedUser.getEmail(), savedUser.getPassword(),
                savedUser.getRole(), savedUser.getStatus(), savedUser.getCreatedAt());
    }
}
