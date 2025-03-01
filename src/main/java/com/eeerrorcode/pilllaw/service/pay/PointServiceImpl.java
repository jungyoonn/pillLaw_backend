package com.eeerrorcode.pilllaw.service.pay;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eeerrorcode.pilllaw.dto.pay.PointDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.member.MemberRole;
import com.eeerrorcode.pilllaw.entity.pay.Pay;
import com.eeerrorcode.pilllaw.entity.pay.Point;
import com.eeerrorcode.pilllaw.entity.pay.Point.PointStatus;
import com.eeerrorcode.pilllaw.repository.MemberRepository;
import com.eeerrorcode.pilllaw.repository.pay.PayRepository;
import com.eeerrorcode.pilllaw.repository.pay.PointRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    @Transactional
    public PointDto addPoints(Long mno, long pointAmount) {
        // mno를 통해 Member 조회
        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 포인트 적립 내역 추가
        Point point = Point.builder()
                .member(member)
                .point(pointAmount) // 적립된 포인트
                .status(PointStatus.EARNED) // 적립 상태
                .endDate(LocalDateTime.now().plusYears(1)) // 예시로 1년 뒤 만기
                .build();

        pointRepository.save(point);

        return toDto(point); // PointDto로 반환
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public int addPointsForCompletedPayments() {
        List<Pay> successfulPayments = payRepository.findByStatus(Pay.PaymentStatus.SUCCESS);
        int pointCount = 0;

        for (Pay payment : successfulPayments) {
            LocalDateTime paymentDate = payment.getRegDate();
            LocalDateTime oneDayLater = paymentDate.plusDays(1); // 하루 뒤로 변경

            if (LocalDateTime.now().isAfter(oneDayLater)) {
                Member member = payment.getOrder().getMember();
                double pointRatio = member.getRoleSet().contains(MemberRole.SUBSCRIBER) ? 0.04 : 0.02;
                long pointsToAdd = (long) (payment.getTotalPrice() * pointRatio);

                Point point = Point.builder()
                        .member(member)
                        .point(pointsToAdd)
                        .status(PointStatus.EARNED)
                        .endDate(LocalDateTime.now().plusYears(1))
                        .build();

                pointRepository.save(point);
                pointCount++;
            }
        }
        return pointCount; // 적립된 포인트 개수 반환
    }

    @Override
    @Transactional
    public PointDto usePoints(Long mno, long pointAmount) {
        // mno를 통해 Member 조회
        Member member = memberRepository.findById(mno)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 포인트 사용 내역 추가
        Point point = Point.builder()
                .member(member)
                .point(-pointAmount) // 사용된 포인트는 음수
                .status(PointStatus.USED) // 사용 상태
                .endDate(LocalDateTime.now().plusYears(1)) // 예시로 1년 뒤 만기
                .build();

        pointRepository.save(point);

        return toDto(point); // PointDto로 반환
    }

    @Override
    public long getTotalPoints(Long mno) {
        List<Point> points = pointRepository.findByMemberMno(mno);

        return points.stream()
                .mapToLong(Point::getPoint)
                .sum();
    }

    @Override
    public List<PointDto> getPointHistory(Long mno) {
        List<Point> points = pointRepository.findByMemberMno(mno);
        return points.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public int deleteExpiredPoints() {
        LocalDateTime now = LocalDateTime.now();
        List<Point> expiredPoints = pointRepository.findByEndDateBefore(now);
        int deletedCount = expiredPoints.size();
        pointRepository.deleteAll(expiredPoints);
        return deletedCount;
    }
}