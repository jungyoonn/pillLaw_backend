package com.eeerrorcode.pilllaw.dto.member;

import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.member.SocialProvider;

import lombok.*;
import lombok.Builder.Default;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SocialMemberDto {
  private String providerId;
  private Long mno;

  @Default
  private List<SocialProvider> providers = new ArrayList<>();

  public void addProvider(SocialProvider provider) {
    if(this.providers == null) {
      this.providers = new ArrayList<>();
    }
    this.providers.add(provider);
  }
}
