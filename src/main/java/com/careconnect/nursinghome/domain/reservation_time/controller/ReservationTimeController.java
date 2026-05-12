package com.careconnect.nursinghome.domain.reservation_time.controller;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeRequestDto;
import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeResponseDto;
import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import com.careconnect.nursinghome.domain.reservation_time.repository.ReservationTimeRepository;
import com.careconnect.nursinghome.domain.reservation_time.service.ReservationTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;
    private final FacilityRepository facilityRepository;
    private final ReservationTimeService reservationTimeService;
    private final ReservationService reservationService;

    @PostMapping("/reservation-times")
    public ResponseEntity<Long> create(@RequestBody ReservationTimeRequestDto requestDto) { // 👈 DTO 이름 바꿈!
        Facility facility = facilityRepository.findById(requestDto.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("시설을 찾을 수 없습니다."));

        ReservationTime reservationTime = ReservationTime.builder()
                .facility(facility)
                .date(requestDto.getDate())
                .startTime(requestDto.getStartTime())
                .endTime(requestDto.getEndTime())
                .maxCapacity(requestDto.getMaxCapacity())
                .currentCapacity(0)
                .status(SlotStatus.AVAILABLE)
                .build();

        ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDto> getDetail(@PathVariable Long id) {
        // 2. 빌드만 하는 게 아니라, 진짜 서비스에서 데이터를 가져와서 넣어주기!
        ReservationResponseDto response = reservationService.getReservationDetail(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/facilities/{facilityId}/times")
    public ResponseEntity<List<ReservationTimeResponseDto>> getTimes(
            @PathVariable Long facilityId,
            @RequestParam(value = "date") String date) { // 👈 서진이는 String으로 보냅니다.

        // 1. 글자를 날짜 객체(LocalDate)로 변환!
        java.time.LocalDate localDate = java.time.LocalDate.parse(date);

        // 2. 변환된 날짜를 서비스에 전달
        return ResponseEntity.ok(reservationTimeService.getTimesByFacilityAndDate(facilityId, localDate));
    }

    @PatchMapping("/facilities/times/{timeId}")
    public ResponseEntity<Void> disable(@PathVariable Long timeId) {
        reservationTimeService.disableSlot(timeId);
        return ResponseEntity.ok().build();
    }
}
