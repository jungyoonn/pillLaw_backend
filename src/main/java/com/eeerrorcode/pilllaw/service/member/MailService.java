package com.eeerrorcode.pilllaw.service.member;

import java.util.Optional;

import com.eeerrorcode.pilllaw.dto.member.EmailDto;
import com.eeerrorcode.pilllaw.entity.member.EmailVerification;

public interface MailService {
  boolean sendVerificationLink(String toEmail);

  Optional<String> verifyEmail(String token);

  void sendEmail(String toEmail, String title, String content);

  void sendHtmlMail(String toEMail, String title, String htmlContent);

  default EmailVerification toEntity(EmailDto dto) {
    EmailVerification emailVerification = EmailVerification.builder()
      .id(dto.getId())
      .email(dto.getEmail())
      .token(dto.getToken())
      .expiresAt(dto.getExpiresAt())
      .build();

    return emailVerification;
  }

  default EmailDto toDto(EmailVerification emailVerification) {
    EmailDto dto = EmailDto.builder()
      .id(emailVerification.getId())
      .email(emailVerification.getEmail())
      .token(emailVerification.getToken())
      .expiresAt(emailVerification.getExpiresAt())
      .build();

    return dto;
  }

  default Optional<EmailDto> toOptionalDto(EmailVerification emailVerification) {
    if(emailVerification == null) {
      return Optional.empty();
    }

    EmailDto dto = EmailDto.builder()
      .id(emailVerification.getId())
      .email(emailVerification.getEmail())
      .token(emailVerification.getToken())
      .expiresAt(emailVerification.getExpiresAt())
      .build();

    return Optional.of(dto);
  }
}
