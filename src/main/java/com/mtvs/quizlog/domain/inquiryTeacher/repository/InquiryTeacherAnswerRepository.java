package com.mtvs.quizlog.domain.inquiryTeacher.repository;

import com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacherAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryTeacherAnswerRepository extends JpaRepository<InquiryTeacherAnswer, Long> {
    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.AnswerDTO(" +
            "a.id, a.title, a.content, a.createdAt, a.updatedAt, a.status, a.inquiryTeacher, a.user" +
            ") " +
            "FROM InquiryTeacherAnswer a " +
            "WHERE a.inquiryTeacher.id = :inquiryTeacherId")
    AnswerDTO findAnswerDTOByInquiryTeacherId(Long inquiryTeacherId);
}
