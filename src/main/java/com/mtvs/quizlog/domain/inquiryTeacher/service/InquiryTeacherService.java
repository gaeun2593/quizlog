package com.mtvs.quizlog.domain.inquiryTeacher.service;


import com.mtvs.quizlog.domain.chapter.dto.request.GetChapterDTO;
import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.inquiryTeacher.dto.*;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacher;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.InquiryTeacherAnswer;
import com.mtvs.quizlog.domain.inquiryTeacher.entity.Status;
import com.mtvs.quizlog.domain.inquiryTeacher.repository.InquiryTeacherAnswerRepository;
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
    private final InquiryTeacherRepository inquiryTeacherRepository;
    private final InquiryTeacherAnswerRepository inquiryTeacherAnswerRepository;


    @Autowired
    public InquiryTeacherService(InquiryTeacherRepository inquiryTeacherRepository,InquiryTeacherAnswerRepository inquiryTeacherAnswerRepository) {
        this.inquiryTeacherRepository = inquiryTeacherRepository;
        this.inquiryTeacherAnswerRepository = inquiryTeacherAnswerRepository;
    }

    //문의 등록
    public InquiryTeacherDTO createInquiry(InquiryTeacherDTO inquiryTeacherDTO,User user,User teacher) {
        log.info("InquiryTeacher: {}", inquiryTeacherDTO.getId());

        try {
            InquiryTeacher inquiry =
                    InquiryTeacher.builder()
                            .title(inquiryTeacherDTO.getTitle())
                            .content(inquiryTeacherDTO.getContent())
                            .status(Status.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .user(user)
                            .teacher(teacher)
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

    public List<InquiryTeacherListDTO> findAllByTeacher(Long teacherId) {
        return inquiryTeacherRepository.findAllListByTeacher(teacherId);
    }

    /**
     * 문의 ID로 문의글 찾는 함수. 반환값 유의
     * @return InquiryTeacherDTO
     * */
    public InquiryTeacherDTO findById(Long inquiryId) {
        InquiryTeacher inquiryTeacher  = inquiryTeacherRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않음"+inquiryId));
        return new InquiryTeacherDTO(inquiryTeacher.getId(),inquiryTeacher.getTitle(),inquiryTeacher.getContent(),inquiryTeacher.getCreatedAt(),inquiryTeacher.getUpdatedAt(),inquiryTeacher.getStatus(),inquiryTeacher.getUser(),inquiryTeacher.getTeacher());
    }

    public void updateInquiry(InquiryTeacherDTO inquiryTeacherDTO,long inquiryId) {
        InquiryTeacher inquiryTeacher  = inquiryTeacherRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않음"+inquiryId));
        // 답변이 없을때만 수정가능
        if(inquiryTeacherAnswerRepository.findAnswerDTOByInquiryTeacherId(inquiryId)==null){
            inquiryTeacher.setTitle(inquiryTeacherDTO.getTitle());
            inquiryTeacher.setContent(inquiryTeacherDTO.getContent());
            inquiryTeacher.setUpdatedAt(LocalDateTime.now());
        }
        else{
           /*팝업창 띄우기*/
        }
    }

    public void deleteInquiry(long inquiryId) {
        InquiryTeacher inquiryTeacher  = inquiryTeacherRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않음"+inquiryId));
        log.info("value"+inquiryTeacherAnswerRepository.findAnswerDTOByInquiryTeacherId(inquiryId));
        // 답변이 없을때만 삭제가능
        if(inquiryTeacherAnswerRepository.findAnswerDTOByInquiryTeacherId(inquiryId)==null){
            inquiryTeacher.setStatus(Status.DELETED);
            inquiryTeacher.setDeletedAt(LocalDateTime.now());
        }
        else{
            /*팝업창 띄우기*/
        }
    }

    public AnswerDTO createAnswer(AnswerDTO answerDTO,InquiryTeacher inquiryTeacher,User user) {
        log.info("InquiryTeacher: {}", answerDTO.getId());
        try {
            InquiryTeacherAnswer answer =
                    InquiryTeacherAnswer.builder()
                            .title(answerDTO.getTitle())
                            .content(answerDTO.getContent())
                            .status(Status.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .inquiryTeacher(inquiryTeacher)
                            .user(user)
                            .build();

            InquiryTeacherAnswer savedAnswer = inquiryTeacherAnswerRepository.save(answer);

            return AnswerDTO.builder()
                    .title(inquiryTeacher.getTitle())
                    .content(inquiryTeacher.getContent())
                    .status(inquiryTeacher.getStatus())
                    .createdAt(inquiryTeacher.getCreatedAt())
                    .user(user)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public InquiryTeacher findEntityById(long inquiryId) {
        InquiryTeacher inquiryTeacher  = inquiryTeacherRepository.findById(inquiryId).orElseThrow(()->new IllegalArgumentException("존재하지 않음"+inquiryId));
        return inquiryTeacher;
    }

    public AnswerDTO findAnswerById(InquiryTeacherDTO inquiry) {
            return inquiryTeacherAnswerRepository.findAnswerDTOByInquiryTeacherId(inquiry.getId());
    }

    public InquiryTeacherAnswer findAnswerById(Long answerId) {
        return inquiryTeacherAnswerRepository.findById(answerId).orElseThrow(()->new IllegalArgumentException("존재하지않는답변"+answerId));
    }

    public void deleteAnswer(Long answerId) {
        InquiryTeacherAnswer inquiryTeacherAnswer = findAnswerById(answerId);
        inquiryTeacherAnswer.setStatus(Status.DELETED);
        inquiryTeacherAnswer.setDeletedAt(LocalDateTime.now());
    }

    public List<InquiryTeacherAllDTO> findAllByAdmin() {
        return inquiryTeacherRepository.findAllListByAdmin();
    }
}

