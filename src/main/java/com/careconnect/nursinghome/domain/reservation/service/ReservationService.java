package com.careconnect.nursinghome.domain.reservation.service;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.repository.MemberRepository;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationRequestDto;
import com.careconnect.nursinghome.domain.reservation.dto.ReservationResponseDto;
import com.careconnect.nursinghome.domain.reservation.entity.Reservation;
import com.careconnect.nursinghome.domain.reservation.entity.ReservationStatus;
import com.careconnect.nursinghome.domain.reservation.repository.ReservationRepository;
import com.careconnect.nursinghome.domain.reservation_time.entity.ReservationTime;
import com.careconnect.nursinghome.domain.reservation_time.entity.SlotStatus;
import com.careconnect.nursinghome.domain.reservation_time.repository.ReservationTimeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;

    @Transactional
    public Long createReservation(Long memberId, ReservationRequestDto requestDto) {
        // 1. 예약하려는 시간 슬롯 조회
        ReservationTime slot = reservationTimeRepository.findById(requestDto.getSlotId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 슬롯입니다."));

        // 2. 예약 가능 여부 체크 (이미 찼거나 비활성화된 경우)
        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new IllegalStateException("선택하신 시간은 이미 예약이 마감되었습니다.");
        }

        // 3. (옵션) 회원(Member)과 시설(Facility) 정보 가져오기
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));



        Facility facility = facilityRepository.findById(requestDto.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("시설 정보를 찾을 수 없습니다."));

        //  인원수 한 명 늘려주기
        slot.setCurrentCapacity(slot.getCurrentCapacity() + 1);

        // 만약 꽉 찼다면 상태를 FULL로 바꿔주기 (선택사항)
        if (slot.getCurrentCapacity() >= slot.getMaxCapacity()) {
            slot.updateStatus(SlotStatus.FULL); // 엔티티에 updateStatus 메서드가 있다면!
        }

        // 4. Builder 패턴으로 예약 객체 생성! (순서 상관없음)
        Reservation reservation = Reservation.builder()
                .member(member) // 미리 조회해온 객체
                .facility(facility)
                .reservationTime(slot)
                .reservationDate(slot.getDate())
                .status(ReservationStatus.PENDING) // 처음엔 대기 상태로
                .build();

        // 5. 슬롯의 현재 인원 업데이트 (슬롯 상태 변경 로직 포함)
        // slot.addReservation(); // 이 메서드는 Entity 안에 구현하면 좋음

        return reservationRepository.save(reservation).getId();
    }

    public ReservationResponseDto getReservationDetail(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        return ReservationResponseDto.from(reservation);
    }

    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 예약을 찾을 수 없습니다."));

        // 상태를 CANCELLED로 바꾸고 거절 사유에 "사용자 취소" 적기
        reservation.updateStatus(ReservationStatus.CANCELLED, "사용자 요청으로 인한 취소");
    }
}
