package com.example.SpringSecurityDemo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

        private final List<Student> students = Arrays.asList(
                        new Student(1, "Mehmet"), new Student(2, "Ahmet"),
                        new Student(3, "Melis"));

        @GetMapping("{studentId}")
        public Student getStudent(
                        @PathVariable("studentId") Integer studentId) {
                return students.stream()
                                .filter(student -> studentId
                                                .equals(student.getStudentId()))
                                .findFirst()
                                .orElseThrow(() -> new IllegalStateException(
                                                "Student " + studentId +
                                                                " does not exist."));
        }
}