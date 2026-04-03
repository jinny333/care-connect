package com.careconnect.nursinghome.domain.reservation.dto;

import com.careconnect.nursinghome.domain.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationResponseDto {
    private Long id;                // 예약 번호
    private String facilityName;    // 시설 이름
    private LocalDate reservationDate; // 예약 날짜
    private LocalDateTime reservationTime; // 예약 시간
    private String status;          // 예약 상태 (PENDING, APPROVED 등)
    private String reason;          // 거절/취소 사유 (있을 경우)

    // 엔티티를 DTO로 변환해주는 정적 메서드 (선택사항, 있으면 편해!)
    public static ReservationResponseDto from(Reservation reservation) {
        return ReservationResponseDto.builder()
                .id(reservation.getId())
                .facilityName(reservation.getFacility().getName())
                .reservationDate(reservation.getReservationDate())
                .reservationTime(reservation.getReservationTimeValue())
                .status(reservation.getStatus().name())
                .reason(reservation.getReason())
                .build();
    }
}
