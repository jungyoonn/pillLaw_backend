package com.eeerrorcode.pilllaw.repository.order;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.order.Payment;
import com.eeerrorcode.pilllaw.entity.pay.Pay;


public interface PayRepository extends JpaRepository<Pay, Long> {
  Optional<Pay> findByOrderOno(Long ono);

}