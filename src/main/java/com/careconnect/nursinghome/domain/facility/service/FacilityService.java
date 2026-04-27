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
                request.getIntroduction(),
                request.getStaff(),
                request.getCapacity(),
                request.getPrograms(),
                request.getOpenDate(),
                request.getFloors(),
                request.getCurrentMale(),
                request.getCurrentFemale(),
                request.getWaitingMale(),
                request.getWaitingFemale(),
                request.getDirections()
        );
        return facilityRepository.save(facility).getId();
    }

    public List<FacilityResponse> getFacilities() {
        return facilityRepository.findAll()
                .stream()
                .map(FacilityResponse::new)
                .collect(Collectors.toList());
    }

    public FacilityResponse getFacility(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("시설 없음 id=" + id));
        return new FacilityResponse(facility);
    }

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
                request.getIntroduction(),
                request.getStaff(),
                request.getCapacity(),
                request.getPrograms(),
                request.getOpenDate(),
                request.getFloors(),
                request.getCurrentMale(),
                request.getCurrentFemale(),
                request.getWaitingMale(),
                request.getWaitingFemale(),
                request.getDirections()
        );
        facilityRepository.save(facility);
    }

    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }

    // 키워드 검색
    public List<FacilityResponse> searchFacilities(String keyword) {
        return facilityRepository.findByKeyword(keyword)
                .stream()
                .map(FacilityResponse::new)
                .collect(Collectors.toList());
    }

    // 위치 기반 검색
    public List<FacilityResponse> getFacilitiesByLocation(Double lat, Double lon, Double radius) {
        return facilityRepository.findByLocation(lat, lon, radius)
                .stream()
                .map(FacilityResponse::new)
                .collect(Collectors.toList());
    }
}