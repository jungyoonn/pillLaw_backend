package com.eeerrorcode.pilllaw.service.member;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.EmailDto;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.member.EmailVerificationRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class MailServiceImpl implements MailService{
  @Autowired
  private JavaMailSender sender;
  @Autowired
  private EmailVerificationRepository repository;
  @Autowired
  private MemberRepository memberRepository;

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Value("${spring.mail.auth-code-expiration-millis}")
  private long expirationTimeMillis;

  @Value("${app.domain}")
  private String appDomain;

  public boolean sendVerificationLink(String toEmail) {
    // 기존 회원일 경우
    if (memberRepository.findByEmail(toEmail).isPresent()) {
      return false;
    }

    // 기존 인증 요청이 있으면 삭제 후 새로 생성
    repository.deleteByEmail(toEmail);
    repository.flush();

    // repository.findByEmail(toEmail).ifPresent(repository::delete);
    log.info("email => {}", toEmail);

    EmailDto dto = new EmailDto(toEmail, expirationTimeMillis);
    repository.save(toEntity(dto));

    // String verificationLink = appDomain + "/api/mail/verify?token=" + verification.getToken();
    String verificationLink = "http://localhost:3000/pilllaw/mypage/email/verify?token=" + dto.getToken();

    String title = "PILL LAW 이메일 인증 링크";
    String content = "<h3 style='color:#7DA9A7'>안녕하세요, PILL LAW입니다!</h3>" +
                      "<p style='color:#7DA9A7'>아래 버튼을 클릭하여 이메일을 인증하세요.</p>" +
                      "<a href='" + verificationLink + "' " +
                      "style='display:inline-block;padding:10px 20px;background-color:#7DA9A7;color:white;text-decoration:none;border-radius:5px;'>이메일 인증</a>" +
                      "<p style='color:green'>이 링크는 10분 동안 유효합니다.</p>";

    sendHtmlMail(toEmail, title, content);
    return true;
  }

  public Optional<String> verifyEmail(String token) {
    Optional<EmailDto> optional = toOptionalDto(repository.findByToken(token).orElse(null));

    if (optional.isEmpty()) {
      return Optional.empty(); // 토큰이 존재하지 않음
    }

    EmailDto dto = optional.get();

    if (dto.isExpired()) {
      repository.delete(toEntity(dto)); // 만료된 인증 정보 삭제
      return Optional.empty();
    }

    // 이메일 인증 성공 -> 데이터베이스에서 삭제 or 상태 업데이트
    String email = dto.getEmail();
    repository.delete(toEntity(dto));
    log.info("인증된 이메일 => {}", email);
    return Optional.of(email);  
  }

  public void sendEmail(String toEmail, String title, String content) {
    SimpleMailMessage emailForm = createEmailForm(toEmail, title, content);
    try {
      sender.send(emailForm);
    } catch (RuntimeException e) {
      log.info("failure sending email");
      throw new RuntimeException();
    }
  }

  // 발신할 이메일 데이터 세팅
  private SimpleMailMessage createEmailForm(String toEmail, String title, String content) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(toEmail);
    message.setSubject(title);
    message.setText(content);
    message.setFrom(fromEmail);

    return message;
  }

  public void sendHtmlMail(String toEMail, String title, String htmlContent) {
    try {
      MimeMessage message = sender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

      helper.setTo(toEMail);
      helper.setSubject(title);
      helper.setText(htmlContent, true); // true -> HTML 형식
      helper.setFrom(fromEmail);

      sender.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("메일 전송 실패", e);
    }
  }

}
