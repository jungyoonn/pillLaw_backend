package com.eeerrorcode.pilllaw.security.service;

import java.util.HashMap;
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

    Map<String, Object> attributes = new HashMap<>();
    // String email = null;
    String providerId = null;
    // String nickname = null;

    if(clientName.equalsIgnoreCase("google")) {
      providerId = oAuth2User.getAttributes().get("sub").toString();
      // email = oAuth2User.getAttributes().get("email").toString();
      // nickname = oAuth2User.getAttributes().get("name").toString();
      attributes = oAuth2User.getAttributes();  // 구글은 그대로 사용
    } else if(clientName.equalsIgnoreCase("naver")) {
      @SuppressWarnings("unchecked")
      Map<String, Object> response = (Map<String, Object>) oAuth2User.getAttributes().get("response");
      log.info("naver response => {}", response);  // 로그 추가
      
      providerId = response.get("id").toString();
      // nickname = response.get("nickname").toString();
      log.info("naver response email => {}", providerId); 
      // log.info("naver response nickname => {}", nickname); 

      attributes = response;
    } else if(clientName.equalsIgnoreCase("kakao")) {
      // Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
      // Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

      providerId = oAuth2User.getAttributes().get("id").toString();
      // email = kakaoAccount.get("email").toString();
      // nickname = profile.get("nickname").toString();
      
      attributes = oAuth2User.getAttributes();
    }


    MemberDto memberDto = saveSocialMember(providerId, clientName);
    SocialMemberDto socialDto = saveSocialInfo(providerId, clientName, memberDto);

    AuthMemberDto authMemberDto = new AuthMemberDto(providerId, memberDto.getPassword()
    , memberDto.getMno(), memberDto.getAccounts(), memberDto.getRoles(), memberDto.getStatus()
    , memberDto.getName(), memberDto.getNickname(), memberDto.getTel(), memberDto.isFirstLogin()
    , memberDto.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList()
    , attributes, socialDto);

    log.info(":::: authMemberDto => {}", authMemberDto);

    return authMemberDto;
  }
  
  @Transactional
  private MemberDto saveSocialMember(String providerId, String clientName) {
    SocialProvider provider = null;

    try {
      provider = SocialProvider.valueOf(clientName.toUpperCase());
    } catch(IllegalArgumentException e) {
      return null;
    }

    Optional<SocialMemberDto> socialOptional = Optional.ofNullable(
      socialService.getByProviderIdAndProvider(providerId, provider)
    ).orElse(Optional.empty());

    if(socialOptional.isPresent()) {
      return service.get(socialOptional.get().getMno()).get();
    }

    // Optional<MemberDto> optional = Optional.ofNullable(
    //   service.getByEmailAndAccount(email, MemberAccount.SOCIAL)
    // ).orElse(Optional.empty());
    
    // if(optional.isPresent()) {
    //   return optional.get();
    // }

    MemberDto dto = MemberDto.builder()
      .password(null)
      .build();

    dto.addAccount(MemberAccount.SOCIAL);
    dto.addRole(MemberRole.USER);
    
    Long mno = service.register(dto);
    log.info("mno는 => {} ", mno);
    dto.setMno(mno);

    SocialMemberDto socialDto = SocialMemberDto.builder()
      .providerId(providerId)
      .mno(mno)
      .socialProvider(provider)
      .build();

    socialService.register(socialDto);

    return dto;
  }

  @Transactional
  private SocialMemberDto saveSocialInfo(String providerId, String clientName, MemberDto memberDto) {
    SocialProvider provider = null;

    try {
      provider = SocialProvider.valueOf(clientName.toUpperCase());
    } catch(IllegalArgumentException e) {
      return null;
    }

    Optional<SocialMemberDto> optional = Optional.ofNullable(
      socialService.getByProviderIdAndProvider(providerId, provider)
    ).orElse(Optional.empty());

    if(optional.isPresent()) {
      return optional.get();
    }

    SocialMemberDto dto = SocialMemberDto.builder()
      .providerId(providerId)
      .mno(memberDto.getMno())
      .socialProvider(provider)
      .build();

    socialService.register(dto);

    return dto;
  }
}
