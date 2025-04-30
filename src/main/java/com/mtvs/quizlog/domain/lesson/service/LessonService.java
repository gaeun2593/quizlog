package com.mtvs.quizlog.domain.lesson.service;



import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;

import com.mtvs.quizlog.domain.lesson.dto.LessonDTO;

import com.mtvs.quizlog.domain.lesson.entity.Lesson;
import com.mtvs.quizlog.domain.lesson.entity.Status;
import com.mtvs.quizlog.domain.lesson.repository.LessonRepository;

import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LessonService {
    private static final Logger log = LoggerFactory.getLogger(LessonService.class);
    private final LessonRepository lessonRepository;
    private final ChapterRepository chapterRepository;
    @Autowired
    public LessonService(LessonRepository lessonRepository, ChapterRepository chapterRepository) {
        this.lessonRepository = lessonRepository;
        this.chapterRepository = chapterRepository;
    }

    //레슨 등록
    public LessonDTO createLesson(LessonDTO lessonDTO ,User user) {
        log.info("Lesson: {}", lessonDTO.getId());
        try {
            Lesson lesson =
                    Lesson.builder()
                            .title(lessonDTO.getTitle())
                            .description(lessonDTO.getDescription())
                            .status(Status.ACTIVE)
                            .createdAt(LocalDateTime.now())
                            .user(user)
                            .build();

            Lesson savelesson = lessonRepository.save(lesson);

            return LessonDTO.builder()
                    .title(savelesson.getTitle())
                    .description(savelesson.getDescription())
                    .status(savelesson.getStatus())
                    .createdAt(savelesson.getCreatedAt())
                    .user(savelesson.getUser())
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    //챕터추가
    @Transactional
    public void addChapterToLesson(Long lessonId, Long chapterId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레슨이 존재하지 않습니다: " + lessonId));
        Chapter chapter = chapterRepository.findChapterById(chapterId);
        lesson.getChapterList().add(chapter);
        lessonRepository.save(lesson); // 변경 감지로 저장됨
    }


    //챕터삭제
    @Transactional
    public void deleteChapterToLesson(Long lessonId, Long chapterId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레슨이 존재하지 않습니다: " + lessonId));
        Chapter chapter = chapterRepository.findChapterById(chapterId);
        lesson.getChapterList().remove(chapter);
        lessonRepository.save(lesson); // 변경 감지로 저장됨
    }


    /** 🔍 전체 레슨 목록 조회 */
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }

    /** 🔍 단일 레슨 조회 */
    public Lesson findLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new IllegalArgumentException("레슨을 찾을 수 없습니다: " + lessonId));
    }

    /** ✏️ 레슨 수정 */
    @Transactional
    public Lesson updateLesson(Long lessonId, Lesson updatedLesson) {
        Lesson lesson = findLessonById(lessonId); // 예외 포함

        lesson.setTitle(updatedLesson.getTitle());
        lesson.setDescription(updatedLesson.getDescription());
        lesson.setUpdatedAt(updatedLesson.getUpdatedAt());

        // 변경 감지에 의해 자동 저장됨
        return lesson;
    }

    /** 🗑️ 레슨 삭제 (소프트 딜리트라면 deletedAt만 설정) */
    @Transactional
    public void deleteLesson(Long lessonId) {
        Lesson lesson = findLessonById(lessonId);
        lesson.setDeletedAt(java.time.LocalDateTime.now());
        lesson.setStatus(Status.DELETED);
    }
}
