package com.mtvs.quizlog.domain.user.repository;

import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findById(Long id);

    @Query("SELECT new com.mtvs.quizlog.domain.user.dto.UserListDTO(u.userId, u.nickname, u.email, u.role, u.status, u.createdAt) FROM User u")
    List<UserListDTO> findAllUsersForAdmin();

    @Query("SELECT new com.mtvs.quizlog.domain.user.dto.UserListDTO(u.userId, u.nickname, u.email, u.role, u.status, u.createdAt) FROM User u WHERE u.status = 'DELETED'")
    List<UserListDTO> findDeletedlUsersForAdmin();
}
