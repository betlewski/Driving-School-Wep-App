package com.project.webapp.drivingschool.service;

import com.project.webapp.drivingschool.model.Course;
import com.project.webapp.drivingschool.model.Student;
import com.project.webapp.drivingschool.repository.CourseRepository;
import com.project.webapp.drivingschool.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

/**
 * Serwis dla kursu nauki jazdy
 */
@Service
public class CourseService {

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Pobranie aktywnego kursu dla kursanta o podanym ID.
     * Jeśli aktywnych kursów jest wiele (sytuacja nie powinna wystąpić),
     * pobierany jest ten, który został najwcześniej rozpoczęty.
     *
     * @param id ID kursanta
     * @return aktywny kurs
     */
    public Optional<Course> getActiveCourseByStudentId(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.flatMap(value -> value.getCourses()
                .stream()
                .filter(Course::isActive)
                .min(Comparator.comparing(Course::getStartDate)));
    }

}
