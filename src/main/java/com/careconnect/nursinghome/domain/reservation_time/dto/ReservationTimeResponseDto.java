package com.careconnect.nursinghome.domain.reservation_time.dto;

import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationTimeResponseDto {
    private Long id;
    private LocalDate date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int currentCapacity;
    private int maxCapacity;
    private SlotStatus status;

    // 엔티티를 DTO로 변환해주는 고마운 메서드!
    public static ReservationTimeResponseDto from(ReservationTime entity) {
        return ReservationTimeResponseDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .currentCapacity(entity.getCurrentCapacity())
                .maxCapacity(entity.getMaxCapacity())
                .status(entity.getStatus())
                .build();
    }
}