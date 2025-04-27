package com.mtvs.quizlog.domain.inquiryTeacher.service;


import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.InquiryTeacherListDTO;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacherAnswer;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import com.mtvs.quizlog.domain.inquiryTeacher.repository.InquiryTeacherRepository;
import com.mtvs.quizlog.domain.user.dto.request.SignUpRequestDTO;
import com.mtvs.quizlog.domain.user.dto.response.SignUpResponseDTO;
import com.mtvs.quizlog.domain.user.entity.User;
import com.mtvs.quizlog.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class InquiryTeacherService {
    private static final Logger log = LoggerFactory.getLogger(InquiryTeacherService.class);
    private  InquiryTeacherRepository inquiryTeacherRepository;


    @Autowired
    public InquiryTeacherService(InquiryTeacherRepository inquiryTeacherRepository) {
        this.inquiryTeacherRepository = inquiryTeacherRepository;
    }

    //문의 등록
    public InquiryTeacherDTO createInquiry(InquiryTeacherDTO inquiryTeacherDTO,User user) {
        log.info("InquiryTeacher: {}", inquiryTeacherDTO.getId());

        try {
            InquiryTeacher inquiry =
                    InquiryTeacher.builder()
                            .title(inquiryTeacherDTO.getTitle())
                            .content(inquiryTeacherDTO.getContent())
                            .status(Status.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .user(user)
                            .build();

            InquiryTeacher inquiryTeacher = inquiryTeacherRepository.save(inquiry);

            return InquiryTeacherDTO.builder()
                                            .title(inquiryTeacher.getTitle())
                                            .content(inquiryTeacher.getContent())
                                            .status(inquiryTeacher.getStatus())
                                            .createdAt(inquiryTeacher.getCreatedAt())
                                            .user(inquiryTeacher.getUser())
                                            .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // 문의 리스트 조회
    public List<InquiryTeacherListDTO> findAll(Long userId) {
        return inquiryTeacherRepository.findAllList(userId);
    }

    public InquiryTeacherDTO findByTeacherId(Long inquiryId, Long teacherId) {
        return inquiryTeacherRepository.findByTeacherId(inquiryId, teacherId);
    }

    public InquiryTeacherDTO findById(Long inquiryId) {
        InquiryTeacher inquiryTeacher  = inquiryTeacherRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않음"+inquiryId));
        return new InquiryTeacherDTO(inquiryTeacher.getId(),inquiryTeacher.getTitle(),inquiryTeacher.getContent(),inquiryTeacher.getCreatedAt(),inquiryTeacher.getUpdatedAt(),inquiryTeacher.getStatus(),inquiryTeacher.getUser());
    }


}
