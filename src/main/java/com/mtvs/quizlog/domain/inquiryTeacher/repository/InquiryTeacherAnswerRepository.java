package com.mtvs.quizlog.domain.inquiryTeacher.repository;

import com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacherAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryTeacherAnswerRepository extends JpaRepository<InquiryTeacherAnswer, Long> {
    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO(" +
            "a.id, a.title, a.content, a.createdAt, a.updatedAt, a.status, a.inquiryTeacher, a.user" +
            ") " +
            "FROM InquiryTeacherAnswer a " +
            "JOIN a.inquiryTeacher t " +
            "WHERE t.id = :inquiryTeacherId " +
            "AND a.status = 'ACTIVE'")
    AnswerDTO findAnswerDTOByInquiryTeacherId(long inquiryTeacherId);

    Optional<Object> findInquiryTeacherAnswerByInquiryTeacher_Id(Long inquiryTeacherId);


    @Query("SELECT a " +
            "FROM InquiryTeacherAnswer a " +
            "JOIN a.inquiryTeacher t " +
            "WHERE t.id = :inquiryTeacherId " +
            "AND a.status = 'ACTIVE'")
    InquiryTeacherAnswer findAnswerByInquiryTeacher(Long inquiryTeacher);
}
