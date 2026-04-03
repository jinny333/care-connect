package com.careconnect.nursinghome.domain.reservation_time.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationTimeRequestDto {
    private Long facilityId;       // 어느 요양원인지
    private LocalDate date;        // 면회 날짜 (예: 2026-04-10)
    private LocalDateTime startTime; // 시작 시간 (예: 2026-04-10T14:00:00)
    private LocalDateTime endTime;   // 종료 시간 (예: 2026-04-10T15:00:00)
    private int maxCapacity;       // 최대 정원 (예: 2명)
}