package com.mtvs.quizlog.domain.lesson.repository;


import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
