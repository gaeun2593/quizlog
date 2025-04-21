package com.mtvs.quizlog.domain.user.service;

import com.mtvs.quizlog.domain.user.dto.request.SignUpRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.SignUpResponseDTO;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public SignUpResponseDTO createUser(SignUpRequestDTO signUpRequestDTO) {

    }
}
