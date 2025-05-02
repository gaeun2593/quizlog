package com.mtvs.quizlog.domain.like.entity;

import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    // 좋아요 누른 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 좋아요 받은 선생님
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    protected Like() {}

    public Like(User user, User teacher) {
        this.user = user;
        this.teacher = teacher;
    }

    public Long getLikeId() {
        return likeId;
    }

    public User getUser() {
        return user;
    }

    public User getTeacher() {
        return teacher;
    }
}
