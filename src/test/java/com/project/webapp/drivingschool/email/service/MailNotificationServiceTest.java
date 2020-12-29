package com.project.webapp.drivingschool.email.service;

import com.project.webapp.drivingschool.data.model.InternalExam;
import com.project.webapp.drivingschool.data.utils.ExamType;
import com.project.webapp.drivingschool.email.utils.MailConstants;
import com.project.webapp.drivingschool.email.utils.MailEventType;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Testy jednostkowe serwisu odpowiadającego za przygotowanie danych do wysyłki
 * podczas uruchomienia zadania cyklicznego.
 */
public class MailNotificationServiceTest {

    private static final MailNotificationService mailNotificationService = new MailNotificationService(null, null, null);

    @Test
    public void test_createMailText() {
        String eventType = "Wykład";
        String startTime = "2020-11-01 10:00:00";
        String endTime = "2020-11-01 12:00:00";
        String addData = "<b>Temat wykładu: </b>Szkolenie BHP<br>";
        String expected = "Witamy!<br>"
                + "Pragniemy przypomnieć o zbliżającym się wydarzeniu, w którym bierzesz udział.<br><br>"
                + "<b>Typ wydarzenia:</b> " + eventType + "<br>"
                + "<b>Czas rozpoczęcia:</b> " + startTime + "<br>"
                + "<b>Czas zakończenia:</b> " + endTime + "<br>"
                + addData
                + "<br>Zaloguj się w aplikacji <a href=\"" + MailConstants.APP_URL_ADDRESS + "\">Szkoła nauki jazdy</a>, by dowiedzieć się więcej.<br><br>"
                + "Liczymy na Twoją obecność.<br>"
                + "Do zobaczenia!<br><br>"
                + "<i>Zespół szkoły nauki jazdy</i>";
        String actual = mailNotificationService.createMailText(eventType, startTime, endTime, addData);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getEventDateToSend() {
        LocalDate expected = LocalDate.now().plusDays(MailConstants.NUMBER_OF_DAYS_BEFORE_EVENT_TO_SEND);
        LocalDate actual = mailNotificationService.getEventDateToSend();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getMailEventTypeByExam_null() {
        InternalExam exam = new InternalExam();
        String expected = "";
        String actual = mailNotificationService.getMailEventTypeByExam(exam);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getMailEventTypeByExam_theoretical() {
        InternalExam exam = new InternalExam();
        exam.setExamType(ExamType.THEORETICAL);
        String expected = MailEventType.THEORY_INTERNAL_EXAM.text;
        String actual = mailNotificationService.getMailEventTypeByExam(exam);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test_getMailEventTypeByExam_practical() {
        InternalExam exam = new InternalExam();
        exam.setExamType(ExamType.PRACTICAL);
        String expected = MailEventType.PRACTICAL_INTERNAL_EXAM.text;
        String actual = mailNotificationService.getMailEventTypeByExam(exam);
        Assert.assertEquals(expected, actual);
    }

}
