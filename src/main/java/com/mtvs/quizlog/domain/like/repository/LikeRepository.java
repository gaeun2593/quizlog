package com.mtvs.quizlog.domain.like.repository;

import com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto;
import com.mtvs.quizlog.domain.like.entity.Like;
import com.mtvs.quizlog.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndTeacher(User user, User teacher);

    // 선생님에 대한 좋아요 수
    int countByTeacher(User teacher);

    // 유저와 선생님 조합으로 좋아요 찾기
    Optional<Like> findByUserAndTeacher(User user, User teacher);


    @Query("""
        SELECT new com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto(l.teacher.nickname, COUNT(l))
        FROM Like l
        WHERE l.teacher.role = 'TEACHER'
        GROUP BY l.teacher.nickname
        ORDER BY COUNT(l) DESC
    """)
    List<TeacherLikeRankingDto> findTop5TeachersByLikes(Pageable pageable);
}
