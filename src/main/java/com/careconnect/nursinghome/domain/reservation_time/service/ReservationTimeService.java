package com.careconnect.nursinghome.domain.reservation_time.service;

import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeResponseDto;
import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import com.careconnect.nursinghome.domain.reservation_time.repository.ReservationTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationTimeService {

    private final ReservationTimeRepository reservationTimeRepository;

    // 특정 시설의 예약 가능 시간 목록 조회
    public List<ReservationTimeResponseDto> getTimesByFacilityAndDate(Long facilityId, LocalDate date) {
        List<ReservationTime> times = reservationTimeRepository.findByFacilityIdAndDate(facilityId, date);

        return times.stream()
                // 예약 가능한(AVAILABLE) 상태인 것만 프런트엔드에 전달
                .filter(t -> t.getStatus() == SlotStatus.AVAILABLE)
                .map(ReservationTimeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void disableSlot(Long timeId) {
        ReservationTime slot = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 시간 슬롯을 찾을 수 없습니다."));

        // 상태를 비활성화(UNAVAILABLE)로 변경!
        slot.updateStatus(SlotStatus.UNAVAILABLE);
    }
}