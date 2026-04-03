package com.careconnect.nursinghome.domain.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {
    private Long slotId;     // 선택한 시간 슬롯 ID
    private Long facilityId; // 시설 ID
    // 예약 날짜나 시간은 슬롯 ID로 조회할 수 있으니까 이 정도만 있음됨
}