package com.careconnect.nursinghome.domain.facility.service;

import com.careconnect.nursinghome.domain.facility.dto.FacilityRequest;
import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.facility.entity.Facility;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    // ✅ 1. 시설 등록
    public Long createFacility(FacilityRequest request) {
        Facility facility = new Facility(
                request.getName(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getPhoneNumber(),
                request.getGrade(),
                request.getPriceRange(),
                request.getDementiaCare(),
                request.getRehabilitation(),
                request.getOperatingHours(),
                request.getIntroduction()
        );

        return facilityRepository.save(facility).getId();
    }

    // ✅ 2. 시설 전체 조회
    public List<FacilityResponse> getFacilities() {
        return facilityRepository.findAll()
                .stream()
                .map(FacilityResponse::new)
                .collect(Collectors.toList());
    }

    // ✅ 3. 시설 단건 조회
    public FacilityResponse getFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시설 없음 id=" + id));

        return new FacilityResponse(facility);
    }

    // ✅ 4. 시설 수정

    @Transactional
    public void updateFacility(Long id, FacilityRequest request) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시설 없음 id=" + id));

        facility.update(
                request.getName(),
                request.getAddress(),
                request.getLatitude(),
                request.getLongitude(),
                request.getPhoneNumber(),
                request.getGrade(),
                request.getPriceRange(),
                request.getDementiaCare(),
                request.getRehabilitation(),
                request.getOperatingHours(),
                request.getIntroduction()
        );

        facilityRepository.save(facility); // 명시적 save
    }

    // ✅ 5. 시설 삭제
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }
}