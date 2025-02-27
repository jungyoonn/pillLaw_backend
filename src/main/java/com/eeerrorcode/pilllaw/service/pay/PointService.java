package com.eeerrorcode.pilllaw.service.pay;

import com.eeerrorcode.pilllaw.dto.pay.PointDto;
import com.eeerrorcode.pilllaw.entity.member.Member;
import com.eeerrorcode.pilllaw.entity.pay.Point;

public interface PointService {

    PointDto addPoints(Long mno, long pointAmount);
    
    int addPointsForCompletedPayments();

    PointDto usePoints(Long mno, long pointAmount);

    long getTotalPoints(Long mno);

    int deleteExpiredPoints();

    // DTO -> Entity 변환
    default Point toEntity(PointDto pointDto) {
        return Point.builder()
                .member(Member.builder().mno(pointDto.getMno()).build())  // Member 객체는 dto에서 번호만 사용
                .point(pointDto.getPoint())  // DTO에서 받은 포인트
                .status(pointDto.getStatus())  // DTO에서 받은 상태
                .endDate(pointDto.getEndDate())  // DTO에서 받은 만료일
                .build();
    }

    // Entity -> DTO 변환
    default PointDto toDto(Point point) {
        return PointDto.builder()
                .pono(point.getPono())
                .mno(point.getMember().getMno())  // Member의 mno
                .point(point.getPoint())  // Point의 값
                .status(point.getStatus())  // Point의 상태
                .endDate(point.getEndDate())  // Point의 만료일
                .regdate(point.getRegDate())
                .moddate(point.getModDate())
                .build();
    }
}
