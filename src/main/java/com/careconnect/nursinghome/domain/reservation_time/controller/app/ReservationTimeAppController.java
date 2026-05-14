package com.careconnect.nursinghome.domain.reservation_time.controller.app;

import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeResponseDto;
import com.careconnect.nursinghome.domain.reservation_time.service.ReservationTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/app/facilities")
@RequiredArgsConstructor
public class ReservationTimeAppController {

    private final ReservationTimeService reservationTimeService;

    // 보호자가 예약 화면에서 시간 선택할 때 호출
    @GetMapping("/{facilityId}/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getTimes(
            @PathVariable Long facilityId,
            @RequestParam(value = "date") String date) {

        java.time.LocalDate localDate = java.time.LocalDate.parse(date);
        return ResponseEntity.ok(reservationTimeService.getTimesByFacilityAndDate(facilityId, localDate));
    }
}