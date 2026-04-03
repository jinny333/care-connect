package com.careconnect.nursinghome.domain.reservation.controller; // 지나의 실제 패키지 경로로 확인!

import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 1. 예약 생성 (POST)
    @PostMapping
    public ResponseEntity<Long> create(@RequestBody ReservationRequestDto requestDto) {
        // 실제로는 JWT에서 memberId를 꺼내야 하지만, 일단 테스트용으로 1번 사용자라고 가정!
        Long memberId = 1L;
        Long reservationId = reservationService.createReservation(memberId, requestDto);
        return ResponseEntity.ok(reservationId);
    }

    // 2. 예약 상세 조회 (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getDetail(@PathVariable Long id) {
        ReservationResponseDto response = reservationService.getReservationDetail(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}