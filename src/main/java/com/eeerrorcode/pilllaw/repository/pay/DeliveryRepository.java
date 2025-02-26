package com.eeerrorcode.pilllaw.repository.pay;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.pay.Delivery;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
  Optional<Delivery> findByOrderOno(Long ono);
}