package com.quizsaas.service;

import com.quizsaas.dto.QuizDTO;
import com.quizsaas.entity.Classroom;
import com.quizsaas.entity.Quiz;
import com.quizsaas.entity.Subject;
import com.quizsaas.entity.User;
import com.quizsaas.repository.ClassroomRepository;
import com.quizsaas.repository.QuizRepository;
import com.quizsaas.repository.SubjectRepository;
import com.quizsaas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private UserRepository userRepository;

    public Quiz createQuiz(QuizDTO dto, Long teacherId) {
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                .orElseThrow(() -> new RuntimeException("Classroom not found"));
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Quiz quiz = new Quiz();
        quiz.setTitle(dto.getTitle());
        quiz.setSubject(subject);
        quiz.setClassroom(classroom);
        quiz.setTeacher(teacher);
        quiz.setDurationMinutes(dto.getDurationMinutes());
        quiz.setStartTime(dto.getStartTime());
        quiz.setEndTime(dto.getEndTime());

        return quizRepository.save(quiz);
    }
}
