package com.mtvs.quizlog.domain.like.repository;

import com.mtvs.quizlog.domain.like.entity.Like;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndTeacher(User user, User teacher);
}
