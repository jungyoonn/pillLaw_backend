package com.eeerrorcode.pilllaw.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.entity.member.LoginResult;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.MemberStatus;
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

  @Transactional
  @Override
  public void modify(MemberDto dto) {
    repository.save(toEntity(dto));    
  }

  @Override
  public Long register(MemberDto dto) {
    dto.addRole(MemberRole.USER);

    // 소셜 멤버일 때
    if(dto.getAccounts().contains(MemberAccount.SOCIAL)) {
      return repository.save(toEntity(dto)).getMno();
    }
    
    if(repository.findByEmail(dto.getEmail()).isPresent()) {
      return null;
    }

    String pw = dto.getPassword();
    dto.setPassword(encoder.encode(pw));
    dto.addAccount(MemberAccount.NORMAL);

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
  public Optional<MemberDto> getByEmailAndAccount(String email, MemberAccount account) {
    return repository.findByEmailAndAccountType(email, account).isPresent() ?
      toOptionalDto(repository.findByEmailAndAccountType(email, account).get()) : null;
  }

  @Override
  public boolean updateEmailVerificationStatus(Long mno, String email) {
    Optional<MemberDto> memberOptional = toOptionalDto(repository.findById(mno).orElse(null));
    
    if (memberOptional.isEmpty()) {
      return false;
    }
    
    MemberDto member = memberOptional.get();
    
    // 이메일 업데이트 및 인증 상태 변경
    member.setEmail(email);
    
    if (!member.getStatus().contains(MemberStatus.VERIFIED)) {
      member.getStatus().add(MemberStatus.VERIFIED);
    }
    
    repository.save(toEntity(member));
    return true;
  }
}

