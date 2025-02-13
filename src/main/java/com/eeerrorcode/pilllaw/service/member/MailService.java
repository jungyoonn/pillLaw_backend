package com.eeerrorcode.pilllaw.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class MailService {
  @Autowired
  private JavaMailSender sender;

  public void sendEmail(String toEmail, String title, String text) {
    SimpleMailMessage emailForm = createEmailForm(toEmail, title, text);
    try {
      sender.send(emailForm);
    } catch (RuntimeException e) {
      log.info("failure sending email");
      throw new RuntimeException();
    }
  }

  // 발신할 이메일 데이터 세팅
  private SimpleMailMessage createEmailForm(String toEmail, String title, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(title);
    message.setText(text);

    return message;
  }
}
