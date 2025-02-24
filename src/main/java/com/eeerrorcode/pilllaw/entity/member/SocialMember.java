package com.eeerrorcode.pilllaw.entity.member;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tbl_social_member")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class SocialMember extends BaseEntity{
  @Id
  private String providerId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno")
  private Member member;

  @Enumerated(EnumType.STRING)
  private SocialProvider socialProvider;

  // public void addSocial(SocialProvider socialProvider) {
  //   socialProviders.add(socialProvider);
  // }
}
