package com.eeerrorcode.pilllaw.entity.order;

import java.util.ArrayList;
import java.util.List;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder.Default;


@Entity(name = "tbl_cart")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "member")
public class Cart extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mno", nullable = false, unique = true)
  private Member member;

  @Builder.Default
  @Setter
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<CartItem> cartItems = new ArrayList<>();  
}
