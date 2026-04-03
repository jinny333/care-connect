package com.careconnect.nursinghome.domain.reservation_time.controller;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.service.ReservationService;
import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeRequestDto;
import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import com.careconnect.nursinghome.domain.reservation_time.repository.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservation-times")
@RequiredArgsConstructor
public class ReservationTimeController {

    private final ReservationTimeRepository reservationTimeRepository;
    private final FacilityRepository facilityRepository;

    @PostMapping
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
        // 예약 상세 조회 로직 (서비스에 추가해야 함!)
        return ResponseEntity.ok().build();
    }
}
