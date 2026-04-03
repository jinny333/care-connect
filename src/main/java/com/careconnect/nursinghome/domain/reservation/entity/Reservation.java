package com.careconnect.nursinghome.domain.reservation.entity;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.reservation.repository.ReservationRepository;
import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseTimeEntity { // 생성/수정일시 자동관리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 예약한 고객(보호자)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id")
    private Facility facility; // 예약된 기관

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id")
    private ReservationTime reservationTime; // 선택한 슬롯 번호
    private LocalDate reservationDate; // 예약 날짜
    private LocalDateTime reservationTimeValue; // 예약 시간 (ERD 기반 중복 저장 시)

    @Enumerated(EnumType.STRING)
    private ReservationStatus status; // PENDING, APPROVED, REJECTED, etc.

    private String reason; // 거절 및 취소 사유

    // 예약 상태 업데이트 메서드
    public void updateStatus(ReservationStatus status, String reason) {
        this.status = status;
        this.reason = reason;
    }

}
