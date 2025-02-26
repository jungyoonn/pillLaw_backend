package com.eeerrorcode.pilllaw.repository.pay;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.entity.pay.Pay.PaymentStatus;

public interface PayRepository extends JpaRepository<Pay, Long> {
  Optional<Pay> findByOrderOno(Long ono);
  List<Pay> findByStatus(PaymentStatus paymentStatus);

}