package com.careconnect.nursinghome.domain.reservation_time.service;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import com.careconnect.nursinghome.domain.reservation_time.dto.ReservationTimeRequestDto;
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
    private final FacilityRepository facilityRepository;

    // [App/Admin 공통] 특정 시설의 예약 가능 시간 목록 조회
    public List<ReservationTimeResponseDto> getTimesByFacilityAndDate(Long facilityId, LocalDate date) {
        List<ReservationTime> times = reservationTimeRepository.findByFacilityIdAndDate(facilityId, date);

        return times.stream()
                .filter(t -> t.getStatus() == SlotStatus.AVAILABLE)
                .map(ReservationTimeResponseDto::from)
                .collect(Collectors.toList());
    }

    // [Admin 전용] 예약 시간 슬롯 생성
    @Transactional
    public Long createReservationTime(ReservationTimeRequestDto requestDto) {
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

        return reservationTimeRepository.save(reservationTime).getId();
    }

    // [Admin 전용] 슬롯 비활성화
    @Transactional
    public void disableSlot(Long timeId) {
        ReservationTime slot = reservationTimeRepository.findById(timeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 시간 슬롯을 찾을 수 없습니다."));
        slot.updateStatus(SlotStatus.UNAVAILABLE);
    }
}