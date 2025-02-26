package com.eeerrorcode.pilllaw.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.pay.Delivery.DeliveryStatus;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryDto {
  private Long dno;
  private Long ono;  
  private Long addrno; 
  private String trackingNumber;
  private DeliveryStatus deliveryStatus;
  private LocalDateTime regdate;
  private LocalDateTime moddate;
}
