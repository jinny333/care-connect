package com.careconnect.nursinghome.domain.reservation.entity;

public enum ReservationStatus {
    PENDING,    // 승인 대기
    APPROVED,   // 승인 완료
    REJECTED,   // 거절 (사유 필요)
    CANCELLED,  // 사용자 취소
    COMPLETED   // 방문 완료
}
