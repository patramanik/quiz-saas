package com.quizsaas.controller;

import com.quizsaas.repository.QuizRepository;
import com.quizsaas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private QuizRepository quizRepository;

    @GetMapping("/quizzes/{classroomId}")
    public ResponseEntity<Object> getQuizzesForClass(@PathVariable Long classroomId) {
        return ResponseBuilder.success("Quizzes fetched", quizRepository.findByClassroomId(classroomId));
    }

    // Add endpoint for attempting quiz
}
