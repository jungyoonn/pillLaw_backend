package com.eeerrorcode.pilllaw.entity.pay;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.eeerrorcode.pilllaw.entity.BaseEntity;
import com.eeerrorcode.pilllaw.entity.member.MemberAddress;
import com.eeerrorcode.pilllaw.entity.order.Order;

@Entity
@Table(name = "tbl_delivery")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ono", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addrno", nullable = false)
    private MemberAddress address;  // 배송지 정보 (MemberAddress와 1:N)

    private String trackingNumber;
    
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DeliveryStatus deliveryStatus = DeliveryStatus.READY;  

    public enum DeliveryStatus {
        READY, SHIPPED, CANCELLED, FINISHED
      }
  
}
