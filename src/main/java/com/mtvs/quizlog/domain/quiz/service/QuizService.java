package com.mtvs.quizlog.domain.quiz.service;

import com.mtvs.quizlog.domain.chapter.dto.request.ChapterDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizDto;
import com.mtvs.quizlog.domain.chapter.dto.request.QuizForm;

import com.mtvs.quizlog.domain.chapter.entity.Chapter;
import com.mtvs.quizlog.domain.chapter.repository.ChapterRepository;
import com.mtvs.quizlog.domain.quiz.dto.QuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import com.mtvs.quizlog.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class QuizService {

    private final QuizRepository quizRepository;
    private final ChapterRepository chapterRepository;


    public void createQuiz(User user , QuizForm quizForm , Chapter chapter ) {

        Quiz quiz = Quiz.createQuiz(user,chapter, quizForm.getWord(), quizForm.getAnswer(), LocalDateTime.now(), LocalDateTime.now());
        if((!quizForm.getWord().isEmpty())&&(!quizForm.getAnswer().isEmpty())){
            quizRepository.save(quiz);
        }

    }

    public void updateQuiz(Long chapterId ,List<QuizForm> quizFormList)  {
        quizFormList.forEach(quizForm -> {

            if (quizForm.getId() == null) {
                Optional<Chapter> chapter = chapterRepository.find(chapterId);
                quizRepository.save(Quiz.createQuiz(chapter.get().getUser(), chapter.get(), quizForm.getWord(), quizForm.getAnswer(), LocalDateTime.now(), LocalDateTime.now()));
            }

            else {
                quizRepository.find(quizForm.getId()).ifPresent(quiz -> {
                    quiz.setTitle(quizForm.getWord());
                    quiz.setAnswer(quizForm.getAnswer());
                    quiz.setUpdatedAt(LocalDateTime.now());
                });

            }
        });

    }



    public List<QuizDto> findbyQuizes(Long userId, Long chapterId) {
       return quizRepository.findbyQuizes(userId , chapterId) ;

    }

    public List<QuizForm>  findAll(Long chapterId) {
       return quizRepository.findAll(chapterId);
    }


    public List<QuizForm> findQuiz(Long chapterId) {
        return quizRepository.findAll(chapterId);
    }

    // 폴더북마크의 퀴즈조회
    public List<QuizDTO> findQuizByFolderBookmarkId(Long userId, int folderBookmarkId) {
        List<Quiz> Quizzes = quizRepository.findQuizzesByUserIdAndFolderBookmarkId(userId,folderBookmarkId);

        // 가져온 챕터들을 챕터 DTO로 바꿈, 리스트로!
        List<QuizDTO> quizDTOList = new ArrayList<>();
        for (Quiz quiz : Quizzes) {
            QuizDTO dto = new QuizDTO(quiz.getTitle());
            quizDTOList.add(dto);
        }

        return quizDTOList;
    }




}
