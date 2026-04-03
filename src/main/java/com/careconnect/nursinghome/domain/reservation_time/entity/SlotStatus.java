package com.careconnect.nursinghome.domain.reservation_time.entity;

public enum SlotStatus {
    AVAILABLE,   // 예약 가능
    UNAVAILABLE, // 예약 불가 (관리자가 막음)
    FULL         // 정원 초과
}
