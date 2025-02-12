package com.eeerrorcode.pilllaw.entity.order;

import java.util.HashSet;
import java.util.Set;

import com.eeerrorcode.pilllaw.entity.BaseEntity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_payment")
@Getter
@Setter
public class Payment extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long payno;

  @OneToOne
  @JoinColumn(name = "ono", nullable = false)
  private Order order;
  private int amount;
  private String method;
  private String cardName;
  private String cardNumber;

  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}
