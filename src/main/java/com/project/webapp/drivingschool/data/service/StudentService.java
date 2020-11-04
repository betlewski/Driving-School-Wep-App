package com.project.webapp.drivingschool.data.service;

import com.project.webapp.drivingschool.data.model.Course;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.repository.StudentRepository;
import com.project.webapp.drivingschool.data.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Serwis dla kursantów (klientów szkoły nauki jazdy)
 */
@Service
public class StudentService {

    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Pobranie wszystkich kursantów
     *
     * @return lista kursantów
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Pobranie studenta na podstawie podanego adresu email
     *
     * @param email adres email
     * @return znaleziony student
     */
    public Student getStudentByEmail(String email) {
        Optional<Student> student = studentRepository.findByEmail(email);
        return student.orElse(null);
    }

    /**
     * Szukanie studenta, do którego został przypisany podany kurs
     *
     * @param course kurs
     * @return znaleziony student
     */
    public Optional<Student> findStudentByCourse(Course course) {
        return studentRepository.findAll().stream()
                .filter(student -> student.getCourses().contains(course))
                .findFirst();
    }

    /**
     * Sprawdzenie, czy podany adres email już istnieje
     *
     * @param email adres email
     * @return true - jeśli adres email istnieje, false - w przeciwnym wypadku
     */
    public Boolean emailExisting(String email) {
        return studentRepository.findByEmail(email).isPresent();
    }

    /**
     * Dodanie nowego kursanta
     *
     * @param student kursant do dodania
     * @return dodany kursant
     */
    public ResponseEntity<Student> addStudent(Student student) {
        if (emailExisting(student.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else if (DataUtils.isPasswordCorrect(student.getPassword())) {
            try {
                student.setPassword(passwordEncoder.encode(student.getPassword()));
                student = studentRepository.save(student);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Edycja danych kursanta na podstawie podanego adresu email
     *
     * @param email      adres email
     * @param newStudent kursant ze zmienionymi danymi
     * @return edytowany kursant
     */
    public ResponseEntity<Student> editStudent(String email, Student newStudent) {
        Optional<Student> oldStudent = studentRepository.findByEmail(email);
        if (oldStudent.isPresent()) {
            Student student = oldStudent.get();
            try {
                student.setFullName(newStudent.getFullName());
                student.setAddress(newStudent.getAddress());
                student.setPhoneNumber(newStudent.getPhoneNumber());
                studentRepository.save(student);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Dodanie numeru PKK kursanta na podstawie podanego adresu email
     *
     * @param email adres email
     * @param pkk   numer PKK
     * @return edytowany kursant
     */
    public ResponseEntity<Student> setStudentPkk(String email, String pkk) {
        Optional<Student> optionalStudent = studentRepository.findByEmail(email);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            try {
                student.setPkk(pkk);
                studentRepository.save(student);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
