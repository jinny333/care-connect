package com.careconnect.nursinghome.domain.reservation_time.dto;

import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class ReservationTimeResponseDto {
    private Long id;
    private String time;    // 프런트엔드에서 쓰는 이름: '9:00' 형태
    private String period;  // 오전/오후 구분: 'am' 또는 'pm'
    private SlotStatus status;
    private int currentCapacity;
    private int maxCapacity;

    public static ReservationTimeResponseDto from(ReservationTime entity) {
        // 1. 시간 포맷팅 (예: 14:30 -> 2:30)
        int hour = entity.getStartTime().getHour();
        int displayHour = (hour > 12) ? hour - 12 : (hour == 0 ? 12 : hour);
        String minute = String.format("%02d", entity.getStartTime().getMinute());

        // 2. am/pm 판별
        String period = (hour < 12) ? "am" : "pm";

        return ReservationTimeResponseDto.builder()
                .id(entity.getId())
                .time(displayHour + ":" + minute)
                .period(period)
                .status(entity.getStatus())
                .currentCapacity(entity.getCurrentCapacity())
                .maxCapacity(entity.getMaxCapacity())
                .build();
    }
}