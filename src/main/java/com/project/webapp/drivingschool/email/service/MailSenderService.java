package com.project.webapp.drivingschool.email.service;

import com.project.webapp.drivingschool.email.utils.MailConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Serwis odpowiadający za wysyłkę powiadomień mailowych
 */
@Service
public class MailSenderService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private JavaMailSender javaMailSender;
    private MailNotificationService mailNotificationService;

    @Autowired
    public MailSenderService(JavaMailSender javaMailSender,
                             MailNotificationService mailNotificationService) {
        this.javaMailSender = javaMailSender;
        this.mailNotificationService = mailNotificationService;
    }

    /**
     * Zadanie cykliczne generujące wysyłkę powiadomień mailowych
     * (czas uruchomienia określony w wyrażeniu CRON)
     */
    @Scheduled(cron = MailConstants.SEND_MAIL_JOB_CRON)
    public void sendMailsJob() {
        logger.info("Started sending mails job...");
        AtomicInteger counter = new AtomicInteger();
        mailNotificationService.getAllNotificationsToSend().forEach(mail -> {
            sendMail(mail.getMailTo(), mail.getSubject(), mail.getText());
            counter.getAndIncrement();
        });
        logger.info("Finished sending mails job. " + counter.get() + " mails have been sent");
    }

    /**
     * Wysłanie na podany adres powiadomienia mailowego
     * o podanym temacie i treści.
     *
     * @param to      adres mail odbiorcy
     * @param subject temat powiadomienia
     * @param text    treść powiadomienia
     */
    private void sendMail(String to, String subject, String text) {
        logger.info("Sending email to: " + to + " about: " + subject);
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            logger.error("Error during email sending: " + e.getMessage());
        }
    }

}