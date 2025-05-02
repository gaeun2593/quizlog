package com.mtvs.quizlog.domain.like.service;

import com.mtvs.quizlog.domain.like.dto.TeacherLikeRankingDto;
import com.mtvs.quizlog.domain.like.entity.Like;
import com.mtvs.quizlog.domain.like.repository.LikeRepository;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    // 좋아요 등록
    @Transactional
    public void like(Long userId, Long teacherId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님 없음"));

        if (likeRepository.existsByUserAndTeacher(user, teacher)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Like like = new Like(user, teacher);
        likeRepository.save(like);
    }

    // 좋아요 취소
    @Transactional
    public void unlike(Long userId, Long teacherId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님 없음"));

        Like like = likeRepository.findByUserAndTeacher(user, teacher)
                .orElseThrow(() -> new IllegalArgumentException("좋아요 기록 없음"));

        likeRepository.delete(like);
    }

    // 현재 유저가 선생님에게 좋아요 눌렀는지 체크
    @Transactional
    public boolean hasLiked(Long userId, Long teacherId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님 없음"));

        return likeRepository.existsByUserAndTeacher(user, teacher);
    }

    // 선생님 조회
    @Transactional
    public User findTeacherById(Long teacherId) {
        return userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님이 존재하지 않습니다."));
    }

    // 선생님 좋아요 수 가져오기
    @Transactional
    public int getLikeCount(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("선생님 없음"));

        return likeRepository.countByTeacher(teacher);
    }

    // 선생님 좋아요 top5
    @Transactional
    public List<TeacherLikeRankingDto> getTop7TeachersByLikes() {
        return likeRepository.findTop7TeachersByLikes(PageRequest.of(0, 7));
    }
}
