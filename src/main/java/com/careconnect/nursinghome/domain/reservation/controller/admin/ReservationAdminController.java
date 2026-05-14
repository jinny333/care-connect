package com.careconnect.nursinghome.domain.reservation.controller.admin;

import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.entity.ReservationStatus;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/reservations") // 👈 경로에 'admin' 추가
@RequiredArgsConstructor
public class ReservationAdminController {

    private final ReservationService reservationService;

    // 관리자용 예약 상태 변경 (승인, 거절 등)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestParam ReservationStatus status,
            @RequestParam(required = false) String rejectReason) {

        reservationService.updateReservationStatus(id, status, rejectReason);
        return ResponseEntity.ok().build();
    }

    // 특정 시설에 들어온 전체 예약 목록 조회 (웹 대시보드용)
    @GetMapping("/facility/{facilityId}")
    public ResponseEntity<List<ReservationResponseDto>> getFacilityReservations(@PathVariable Long facilityId) {
        // 이 로직은 Service에 추가가 필요할 수도 있어요! (시설 ID로 전체 조회)
        return ResponseEntity.ok(reservationService.getReservationsByFacility(facilityId));
    }
}