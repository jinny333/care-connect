package com.careconnect.nursinghome.domain.reservation_time.controller.admin;

import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeRequestDto;
import com.careconnect.nursinghome.domain.reservation_time.service.ReservationTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/reservation-times")
@RequiredArgsConstructor
public class ReservationTimeAdminController {

    private final ReservationTimeService reservationTimeService;

    // 관리자 웹에서 예약 타임 슬롯 생성
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ReservationTimeRequestDto requestDto) {
        return ResponseEntity.ok(reservationTimeService.createReservationTime(requestDto));
    }

    // 관리자가 특정 시간 슬롯을 닫음 (비활성화)
    @PatchMapping("/{timeId}/disable")
    public ResponseEntity<Void> disable(@PathVariable Long timeId) {
        reservationTimeService.disableSlot(timeId);
        return ResponseEntity.ok().build();
    }
}