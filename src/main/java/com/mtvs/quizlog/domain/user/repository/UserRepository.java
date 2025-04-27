package com.mtvs.quizlog.domain.user.repository;

import com.mtvs.quizlog.domain.user.entity.Status;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findById(Long id);
    Page<User> findByStatus(Status status, Pageable pageable);
    Page<User> findByStatusAndNicknameContainingIgnoreCase(Status status, String nickname, Pageable pageable);
}
