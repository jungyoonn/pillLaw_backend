package com.eeerrorcode.pilllaw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eeerrorcode.pilllaw.dto.pay.PointDto;
import com.eeerrorcode.pilllaw.service.pay.PointService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/point")
@RequiredArgsConstructor
public class PointController {
    @Autowired
    private PointService pointService;

     // 포인트 적립 API
    @PostMapping("/{mno}/add")
    public ResponseEntity<PointDto> addPoints(@PathVariable Long mno, @RequestParam long pointAmount) {
        PointDto pointDto = pointService.addPoints(mno, pointAmount);
        return ResponseEntity.ok(pointDto);
    }

    // 포인트 사용 API
    @PostMapping("/{mno}/use")
    public ResponseEntity<PointDto> usePoints(@PathVariable Long mno, @RequestParam long pointAmount) {
        PointDto pointDto = pointService.usePoints(mno, pointAmount);
        return ResponseEntity.ok(pointDto);
    }

    // 총 포인트 조회 API
    @GetMapping("/{mno}/total")
    public ResponseEntity<Long> getTotalPoints(@PathVariable Long mno) {
        long totalPoints = pointService.getTotalPoints(mno);
        return ResponseEntity.ok(totalPoints);
    }

    // 만기된 포인트 삭제 API (테스트용)
    @DeleteMapping("/expired")
    public ResponseEntity<Integer> deleteExpiredPoints() {
        int deletedCount = pointService.deleteExpiredPoints();
        return ResponseEntity.ok(deletedCount);
    }

    // ✅ **포인트 적립 스케줄링 강제 실행 API (테스트용)**
    @GetMapping("/scheduled-add")
    public ResponseEntity<Integer> triggerScheduledPointAddition() {
        int result = pointService.addPointsForCompletedPayments();
        return ResponseEntity.ok(result);
    }
}
