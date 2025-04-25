package com.mtvs.quizlog.domain.like.service;

import com.mtvs.quizlog.domain.like.dto.LikeDTO;
import com.mtvs.quizlog.domain.like.entity.Like;
import com.mtvs.quizlog.domain.like.repository.LikeRepository;
import com.mtvs.quizlog.domain.user.controller.UserController;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    // 좋아요 누르기
    @Transactional
    public void like(LikeDTO likeDTO) {

        // 본인에게 좋아요 누르기 불가능
        if (likeDTO.getUserId().equals(likeDTO.getTeacherId())) {
            throw new IllegalArgumentException("본인에게는 좋아요를 누를 수 없습니다.");
        }

        // 유저
        User user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        // 선생님
        User teacher = userRepository.findById(likeDTO.getTeacherId())
                .orElseThrow(() -> new IllegalArgumentException("선생님 없음"));

        // 이미 좋아요 눌렀는지 체크
        if (likeRepository.existsByUserAndTeacher(user, teacher)) {
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        Like like = new Like(user, teacher);
        likeRepository.save(like);
    }

    // 선생님 조회
    @Transactional
    public User findTeacherById(Long teacherId) {
        User teacher = userRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("선생님이 존재하지 않습니다."));
        return new User(teacher.getUserId(), teacher.getNickname(), teacher.getEmail(), teacher.getPassword(), teacher.getRole(), teacher.getStatus(), teacher.getCreatedAt(), teacher.getUpdatedAt(), teacher.getDeletedAt());
    }
}
