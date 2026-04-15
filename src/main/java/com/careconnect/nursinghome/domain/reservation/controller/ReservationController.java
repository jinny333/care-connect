package com.careconnect.nursinghome.domain.reservation.controller; // 지나의 실제 패키지 경로로 확인!

import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.entity.ReservationStatus;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 1. 예약 생성 (POST)
    @PostMapping
    public ResponseEntity<Long> create(
            Principal principal, // 인증 객체 추가
            @RequestBody ReservationRequestDto requestDto) {

        // principal.getName()을 통해 토큰에 담긴 사용자 ID(숫자)를 꺼냅니다.
        Long memberId = Long.parseLong(principal.getName());

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

    // 내 예약 목록 조회 (표에 있는 /me 주소!)
    @GetMapping("/me")
    public ResponseEntity<List<ReservationResponseDto>> getMyList(
            Principal principal) { // 인증 객체 추가

        // 여기서도 1L 대신 로그인한 유저의 ID를 사용합니다!
        Long memberId = Long.parseLong(principal.getName());

        return ResponseEntity.ok(reservationService.getMyReservations(memberId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status,
            @RequestParam(required = false) String rejectReason) {

        reservationService.updateReservationStatus(id, status, rejectReason);
        return ResponseEntity.ok().build();
    }
}