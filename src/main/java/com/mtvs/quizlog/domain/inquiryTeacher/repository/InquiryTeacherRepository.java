package com.mtvs.quizlog.domain.inquiryTeacher.repository;

import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.user.dto.UserListDTO;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.net.InterfaceAddress;
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
            "i.inquiryTeacherId, i.title,i.content, i.createdAt, i.updatedAt, i.status, u.nickname, c.title) " +
            "FROM InquiryTeacher i " +
            "JOIN i.user u " +
            "JOIN i.chapter c")
    List<InquiryTeacherListDTO> findAllList();


    @Query("SELECT new com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO(" +
            "i.inquiryTeacherId, i.title, i.content, i.createdAt, i.updatedAt, i.status, u.nickname, c.title) " +
            "FROM InquiryTeacher i " +
            "JOIN i.user u " +
            "JOIN i.chapter c " +
            "WHERE c.id = :chapterId " +
            "AND u.userId = :userId"
    )
    InquiryTeacherListDTO findByChapterId(Long chapterId,Long userId);
}
