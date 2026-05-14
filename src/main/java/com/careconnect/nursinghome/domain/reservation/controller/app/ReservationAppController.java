package com.careconnect.nursinghome.domain.reservation.controller.app;

import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/app/reservations") // 👈 경로에 'app' 추가
@RequiredArgsConstructor
public class ReservationAppController {

    private final ReservationService reservationService;

    // 예약 생성 (앱 사용자가 신청)
    @PostMapping
    public ResponseEntity<Long> create(Principal principal, @RequestBody ReservationRequestDto requestDto) {
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(reservationService.createReservation(memberId, requestDto));
    }

    // 내 예약 목록 조회
    @GetMapping("/me")
    public ResponseEntity<List<ReservationResponseDto>> getMyList(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ResponseEntity.ok(reservationService.getMyReservations(memberId));
    }

    // 내 예약 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getDetail(@PathVariable Long id) {
        return ResponseEntity.ok(reservationService.getReservationDetail(id));
    }

    // 예약 취소 (사용자 본인이 취소)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build();
    }
}