package com.quizsaas.controller;

import com.quizsaas.dto.QuizDTO;
import com.quizsaas.security.CustomUserDetails;
import com.quizsaas.service.QuizService;
import com.quizsaas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/quizzes")
    public ResponseEntity<Object> createQuiz(@RequestBody QuizDTO quizDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseBuilder.success("Quiz created", quizService.createQuiz(quizDTO, userDetails.getUser().getId()));
    }
}
