package com.mtvs.quizlog.domain.inquiryTeacher.repository;

import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherAllDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
/*  InquiryTeacherList DTO
*   private Long inquiryTeacherId; //PK
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private User user; //작성한 유저
    private Chapter chapter; //chapterId
* */
@Repository
public interface InquiryTeacherRepository extends JpaRepository<InquiryTeacher, Long> {

    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO(" +
            "i.id, i.title,i.content, i.createdAt, i.updatedAt, i.status, u.nickname) " +
            "FROM InquiryTeacher i " +
            "JOIN i.user u " +
            "WHERE u.userId = :userId " +
            "AND i.status = 'ACTIVE'")
    List<InquiryTeacherListDTO> findAllList(long userId);

    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO(" +
            "i.id, i.title,i.content, i.createdAt, i.updatedAt, i.status, u.nickname) " +
            "FROM InquiryTeacher i " +
            "JOIN i.teacher t " +
            "JOIN i.user u " +
            "WHERE t.userId = :teacherId " +
            "AND i.status = 'ACTIVE'")
    List<InquiryTeacherListDTO> findAllListByTeacher(long teacherId);

    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherAllDTO(" +
            "i.id,i.title,i.content,i.createdAt,i.updatedAt,i.deletedAt,i.status,i.user.userId,i.teacher.userId) " +
            "FROM InquiryTeacher i"
           )
    List<InquiryTeacherAllDTO> findAllListByAdmin();
}
