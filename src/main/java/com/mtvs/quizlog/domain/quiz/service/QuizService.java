package com.mtvs.quizlog.domain.quiz.service;

import com.mtvs.quizlog.domain.quiz.dto.QuizDTO;
import com.mtvs.quizlog.domain.quiz.entity.Quiz;
import com.mtvs.quizlog.domain.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional
    public QuizDTO createQuiz(QuizDTO quizDTO) {
        Optional<Quiz> findQuiz = quizRepository.findByQuizTitle((quizDTO.getTitle());

        if(findQuiz.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 퀴즈 제목입니다. : " + quizDTO.getTitle());
        }

        Quiz quiz = new Quiz(quizDTO.getTitle(),quizDTO.getAnswer());
        Quiz saveQuiz =quizRepository.save(quiz);
        return new QuizDTO(saveQuiz.getTitle(),saveQuiz.getAnswer());

    }

    @Transactional
    public void deleteQuiz(Long quizId) {
        boolean result = quizRepository.existsById(quizId);

        if(!result) {
            throw new IllegalArgumentException("게시글이 존재하지 않습니다. " + quizId);
        }
        quizRepository.deleteById(quizId);
    }


}
