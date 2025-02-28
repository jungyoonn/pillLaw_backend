package com.eeerrorcode.pilllaw.dto.member;

import java.util.List;

import com.eeerrorcode.pilllaw.dto.board.ProductReviewDto;
import com.eeerrorcode.pilllaw.dto.product.ProductFavoriteDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfoDto {
  private String nickname;
  private Integer following;
  private Integer follower;
  private boolean isfollowing;
  private boolean followBack;

  private List<ProductReviewDto> reviewDto;
  private List<ProductFavoriteDto> favoriteDto;
}
