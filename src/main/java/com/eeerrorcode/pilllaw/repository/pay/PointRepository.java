package com.eeerrorcode.pilllaw.repository.pay;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeerrorcode.pilllaw.entity.pay.Point;

public interface PointRepository extends JpaRepository<Point,Long> {
    // 회원별 포인트 조회
    List<Point> findByMemberMno(Long mno);

    // 만료된 포인트 조회 (만료일이 현재 시간보다 이전인 포인트)
    List<Point> findByEndDateBefore(LocalDateTime now);
} 