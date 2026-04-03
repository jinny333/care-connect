package com.careconnect.nursinghome.domain.reservation_time.entity;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 슬롯 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility; // 기관 번호

    private LocalDate date;      // 날짜
    private LocalDateTime startTime; // 시작 시간
    private LocalDateTime endTime;   // 종료 시간

    private Integer maxCapacity;    // 최대 정원
    private Integer currentCapacity; // 현재 예약 인원

    @Enumerated(EnumType.STRING)
    private SlotStatus status; // AVAILABLE, UNAVAILABLE, FULL

    // 상태 변경 편의 메서드 (명세서의 PATCH 기능을 위해)
    public void disableSlot() {
        this.status = SlotStatus.UNAVAILABLE;
    }

    // 1. 현재 인원수를 직접 설정하는 메서드
    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }

    // 2. 상태(AVAILABLE, FULL 등)를 바꾸는 메서드
    public void updateStatus(SlotStatus status) {
        this.status = status;
    }
}