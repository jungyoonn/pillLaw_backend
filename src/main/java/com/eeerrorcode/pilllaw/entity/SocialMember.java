package com.eeerrorcode.pilllaw.entity;

import java.util.HashSet;
import java.util.Set;

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

  @Builder.Default
  @ElementCollection(fetch = FetchType.LAZY)
  private Set<SocialProvider> socialProviders = new HashSet<>();

  public void addSocial(SocialProvider socialProvider) {
    socialProviders.add(socialProvider);
  }
}
