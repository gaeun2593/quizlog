package com.mtvs.quizlog.domain.lesson.repository;


import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {


    /** 🔍 전체 레슨 목록 조회 */
    @Query("SELECT l from Lesson l WHERE l.status = 'ACTIVE' AND l.user.userId= :teacherId ")
    public List<Lesson> findAllLessons(long teacherId) ;

    /** 🔍 단일 레슨 조회 */
    @Query("SELECT l from Lesson l WHERE l.status = 'ACTIVE' AND l.id= :lessonId ")
    public Lesson findLessonById(Long lessonId) ;


}
