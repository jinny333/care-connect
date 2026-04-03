package com.careconnect.nursinghome.domain.reservation.repository;

import com.careconnect.nursinghome.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    // 기본 CRUD(저장, 조회, 삭제)는 JpaRepository가 해줌
}