package dev.sh1on.amlethmp.common.shared.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Lớp nghiệp vụ đảm nhiệm tác vụ gửi mail tự động.
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender mailSender;
    private final Environment environment;

    public void sendMail(String[] to,
                         String @Nullable [] cc,
                         String @Nullable [] bcc,
                         String subject,
                         String body,
                         @Nullable String replyTo) {

        var message = new SimpleMailMessage();
        message.setFrom(environment.getProperty("mail.from"));
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setCc(cc);
        message.setBcc(bcc);
        message.setSentDate(new Date());
        message.setReplyTo(replyTo);

        try {
            mailSender.send(message);
        } catch (MailException e) {
            log.warn(e.getLocalizedMessage());
        }
    }
}
