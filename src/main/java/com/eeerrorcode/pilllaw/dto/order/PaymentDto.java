package com.eeerrorcode.pilllaw.dto.order;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {
  private Long payno;
  private Long ono;
  private long amount;
  private String method;
  private String cardName;
  private String cardNumber;
  private String paymentStatus;
  private LocalDateTime regdate, moddate;
}
