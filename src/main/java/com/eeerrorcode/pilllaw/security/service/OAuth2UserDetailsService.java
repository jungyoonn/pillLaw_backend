package com.eeerrorcode.pilllaw.security.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.eeerrorcode.pilllaw.dto.member.MemberDto;
import com.eeerrorcode.pilllaw.dto.member.SocialMemberDto;
import com.eeerrorcode.pilllaw.entity.member.MemberAccount;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.member.SocialProvider;
import com.eeerrorcode.pilllaw.security.dto.AuthMemberDto;
import com.eeerrorcode.pilllaw.service.member.MemberService;
import com.eeerrorcode.pilllaw.service.member.SocialMemberService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OAuth2UserDetailsService extends DefaultOAuth2UserService{
  @Autowired
  private MemberService service;
  @Autowired
  private SocialMemberService socialService;

  @Override
  @Transactional
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("userRequest !!! => {}", userRequest);

    String clientName = userRequest.getClientRegistration().getClientName(); // 어떤 소셜인지 확인
    Map<?,?> params = userRequest.getAdditionalParameters();
    OAuth2User oAuth2User = super.loadUser(userRequest);

    log.info("clientName ::: => {}", clientName);
    log.info("params ::: => {}", params);

    // 어떤 attribute들이 있나 확인용 로그
    oAuth2User.getAttributes().forEach((k, v) -> {
      log.info("key => {}", k);
      log.info("value => {}", v);
    });

    String email = null;
    if(clientName.equalsIgnoreCase("google")) {
      email = oAuth2User.getAttributes().get("email").toString();
    }

    // String nickname = oAuth2User.getAttributes().get("name").toString();

    MemberDto memberDto = saveSocialMember(email);
    SocialMemberDto socialDto = saveSocialInfo(email, clientName, memberDto);

    AuthMemberDto authMemberDto = new AuthMemberDto(memberDto.getEmail(), memberDto.getPassword()
    , memberDto.getMno(), memberDto.getAccounts(), memberDto.getRoles(), memberDto.getStatus()
    , memberDto.getName(), memberDto.getNickname(), memberDto.getTel(), memberDto.isFirstLogin()
    , memberDto.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList()
    , oAuth2User.getAttributes(), socialDto);

    return authMemberDto;
  }
  
  @Transactional
  private MemberDto saveSocialMember(String email) {
    Optional<MemberDto> optional = Optional.ofNullable(
      service.getByEmailAndAccount(email, MemberAccount.SOCIAL)
    ).orElse(Optional.empty());
    
    if(optional.isPresent()) {
      return optional.get();
    }

    MemberDto dto = MemberDto.builder()
      .email(email)
      .nickname(email)
      .password(null)
      .build();

    dto.addAccount(MemberAccount.SOCIAL);
    dto.addRole(MemberRole.USER);
    
    service.register(dto);
    // dto.setMno(service.register(dto));

    return dto;
  }

  @Transactional
  private SocialMemberDto saveSocialInfo(String email, String clientName, MemberDto memberDto) {
    SocialProvider provider = null;

    try {
      provider = SocialProvider.valueOf(clientName.toUpperCase());
    } catch(IllegalArgumentException e) {
      return null;
    }

    Optional<SocialMemberDto> optional = Optional.ofNullable(
      socialService.getByProviderIdAndProvider(email, provider)
    ).orElse(Optional.empty());

    if(optional.isPresent()) {
      return optional.get();
    }

    SocialMemberDto dto = SocialMemberDto.builder()
      .providerId(email)
      .mno(memberDto.getMno())
      .build();

    dto.addProvider(provider);
    socialService.register(dto);

    return dto;
  }
}
