package com.project.webapp.drivingschool.email.service;

import com.project.webapp.drivingschool.data.model.DrivingLesson;
import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.model.Lecture;
import com.project.webapp.drivingschool.data.model.Student;
import com.project.webapp.drivingschool.data.service.DrivingLessonService;
import com.project.webapp.drivingschool.data.service.InternalExamService;
import com.project.webapp.drivingschool.data.service.LectureService;
import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.email.model.MailNotification;
import com.project.webapp.drivingschool.email.utils.MailEventType;
import com.project.webapp.drivingschool.email.utils.MailConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.project.webapp.drivingschool.email.utils.MailConstants.*;
import static com.project.webapp.drivingschool.email.utils.MailEventType.*;

/**
 * Serwis odpowiadający za przygotowanie danych do wysyłki
 * podczas uruchomienia zadania cyklicznego.
 */
@Service
public class MailNotificationService {

    private LectureService lectureService;
    private DrivingLessonService drivingLessonService;
    private InternalExamService internalExamService;

    @Autowired
    public MailNotificationService(LectureService lectureService,
                                   DrivingLessonService drivingLessonService,
                                   InternalExamService internalExamService) {
        this.lectureService = lectureService;
        this.drivingLessonService = drivingLessonService;
        this.internalExamService = internalExamService;
    }

    /**
     * Pobranie wszystkich danych gotowych do wysyłki.
     *
     * @return lista powiadomień mailowych
     */
    public List<MailNotification> getAllNotificationsToSend() {
        List<MailNotification> list = new ArrayList<>();
        getLectureNotificationsToSend(list);
        getDrivingLessonNotificationsToSend(list);
        getExamNotificationsToSend(list);
        return list;
    }

    /**
     * Pobranie danych do wysyłki
     * o typie wydarzenia: wykłady.
     *
     * @param list lista wynikowa
     */
    private void getLectureNotificationsToSend(List<MailNotification> list) {
        Map<Student, List<Lecture>> map =
                lectureService.getMapStudentsWithActualLecturesByLectureStartDate(getEventDateToSend());
        for (Student student : map.keySet()) {
            String mailTo = student.getEmail();
            String eventType = MailEventType.LECTURE.text;
            String subject = MailConstants.SUBJECT.replaceAll(EVENT_TYPE_HASH, eventType);
            map.get(student).forEach(lecture -> {
                String text = createMailText(eventType,
                        lecture.getStartTime().format(FORMATTER), lecture.getEndTime().format(FORMATTER),
                        "<b>Temat wykładu: </b>" + lecture.getSubject() + "<br>");
                MailNotification mail = new MailNotification(mailTo, subject, text);
                list.add(mail);
            });
        }
    }

    /**
     * Pobranie danych do wysyłki
     * o typie wydarzenia: jazdy szkoleniowe.
     *
     * @param list lista wynikowa
     */
    private void getDrivingLessonNotificationsToSend(List<MailNotification> list) {
        Map<Student, List<DrivingLesson>> map =
                drivingLessonService.getMapStudentsWithAcceptedDrivingLessonsByLessonStartDate(getEventDateToSend());
        for (Student student : map.keySet()) {
            String mailTo = student.getEmail();
            String eventType = MailEventType.DRIVING_LESSON.text;
            String subject = MailConstants.SUBJECT.replaceAll(EVENT_TYPE_HASH, eventType);
            map.get(student).forEach(lesson -> {
                String text = createMailText(eventType,
                        lesson.getStartTime().format(FORMATTER), lesson.getEndTime().format(FORMATTER),
                        "<b>Instruktor prowadzący: </b>" + lesson.getEmployee().getFullName()
                                + " (" + lesson.getEmployee().getPhoneNumber() + ")<br>");
                MailNotification mail = new MailNotification(mailTo, subject, text);
                list.add(mail);
            });
        }
    }

    /**
     * Pobranie danych do wysyłki
     * o typie wydarzenia: egzaminy wewnętrzne.
     *
     * @param list lista wynikowa
     */
    private void getExamNotificationsToSend(List<MailNotification> list) {
        Map<Student, List<InternalExam>> map =
                internalExamService.getMapStudentsWithAcceptedInternalExamsByExamStartDate(getEventDateToSend());
        for (Student student : map.keySet()) {
            String mailTo = student.getEmail();
            map.get(student).forEach(exam -> {
                String eventType = getMailEventTypeByExam(exam);
                String subject = MailConstants.SUBJECT.replaceAll(EVENT_TYPE_HASH, eventType);
                String text = createMailText(eventType,
                        exam.getStartTime().format(FORMATTER), exam.getEndTime().format(FORMATTER),
                        "<b>Osoba prowadząca: </b>" + exam.getEmployee().getFullName()
                                + " (" + exam.getEmployee().getPhoneNumber() + ")<br>");
                MailNotification mail = new MailNotification(mailTo, subject, text);
                list.add(mail);
            });
        }
    }

    /**
     * Przygotowanie treści powiadomienia
     * na podstawie przekazanych danych.
     *
     * @param eventType typ wydarzenia
     * @param startTime czas rozpoczęcia
     * @param endTime   czas zakończenia
     * @param addData   dane dodatkowe
     * @return treść wiadomości
     */
    public String createMailText(String eventType, String startTime, String endTime, String addData) {
        return MailConstants.TEXT
                .replaceAll(EVENT_TYPE_HASH, eventType)
                .replaceAll(START_TIME_HASH, startTime)
                .replaceAll(END_TIME_HASH, endTime)
                .replaceAll(ADD_DATA_HASH, addData);
    }

    /**
     * Pobranie daty wydarzenia,
     * na które ma zostać wysłane powiadomienie.
     *
     * @return data wydarzenia
     */
    public LocalDate getEventDateToSend() {
        return LocalDate.now().plusDays(NUMBER_OF_DAYS_BEFORE_EVENT_TO_SEND);
    }

    /**
     * Pobranie tekstu na podstawie typu egzaminu.
     *
     * @param exam egzamin wewnętrzny
     * @return tekst wykorzystywany w powiadomieniu
     */
    public String getMailEventTypeByExam(InternalExam exam) {
        MailEventType eventType = null;
        if (ExamType.THEORETICAL.equals(exam.getExamType())) {
            eventType = THEORY_INTERNAL_EXAM;
        } else if (ExamType.PRACTICAL.equals(exam.getExamType())) {
            eventType = PRACTICAL_INTERNAL_EXAM;
        }
        return eventType == null ? "" : eventType.text;
    }

}
