package com.careconnect.nursinghome.domain.reservation_time.repository;

import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.LocalDate;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Long> {
    // 특정 기관의 특정 날짜 슬롯만 조회하는 쿼리 메소드 추가하면 좋음
    List<ReservationTime> findByFacilityIdAndDate(Long facilityId, LocalDate date);
    // 특정 시설의 모든 예약 가능 시간 가져오기
    List<ReservationTime> findAllByFacilityId(Long facilityId);
}