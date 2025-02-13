package com.eeerrorcode.pilllaw.service.member;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.EmailDto;
import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class MemberServiceImpl implements MemberService{
  @Autowired
  private MemberRepository repository;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private MailService mailService;

  @Value("${spring.mail.auth-code-expiration-millis}")
  private long authCodeExpirationMillis;

  @Override
  public Optional<MemberDto> get(Long mno) {
    return repository.findById(mno).map(this::toDto);
  }

  @Override
  public Optional<MemberDto> getByEmail(String email) {
    return toOptionalDto(repository.findByEmail(email).orElse(null));
  }

  @Override
  public List<MemberDto> listAll() {
    return repository.findAll().stream().map(this::toDto).toList();
  }

  @Override
  public void modify(MemberDto dto) {
    repository.save(toEntity(dto));    
  }

  @Override
  public Long register(MemberDto dto) {
    if(repository.findByEmail(dto.getEmail()) != null) {
      return null;
    }
    return repository.save(toEntity(dto)).getMno();
  }

  @Override
  public void remove(MemberDto dto) {
    repository.delete(toEntity(dto));    
  }

  @Override
  public LoginResult login(String email, String pw) {
    Optional<MemberDto> optional = getByEmail(email);

    if (optional.isEmpty()) {
      return LoginResult.EMAIL_NOT_FOUND;
    }
    MemberDto dto = optional.get();

    if (!encoder.matches(pw, dto.getPassword())) {
        return LoginResult.PASSWORD_MISMATCH;
    }

    return LoginResult.SUCCESS;
  }

  @Override
  public EmailDto sendCodeToEmail(String email) {
    if(checkDuplicatedEmail(email)) {
      return null;
    }

    String title = "Pill Law 인증번호 발송";
    String authCode = this.createCode();

    mailService.sendEmail(email, title, authCode);

    EmailDto dto = EmailDto.builder().email(email).authCode(authCode).build();

    return dto;
  }

  @Override
  public boolean checkDuplicatedEmail(String email) {
    Optional<MemberDto> optional = toOptionalDto(repository.findByEmail(email).orElse(null));  
    if(optional.isEmpty()) {
      return false;
    }
    return true;
  }

  @Override
  public String createCode() {
    int length = 6;
    Random random;
    StringBuilder builder = new StringBuilder();

    try {
      random = SecureRandom.getInstanceStrong();
      for (int i = 0; i < length; i++) {
        builder.append(random.nextInt(10));
      }
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    
    return builder.toString();
  }

  @Override
  public boolean verifiedCode(String email, String inputCode) {
    return false;
  }
  
}
