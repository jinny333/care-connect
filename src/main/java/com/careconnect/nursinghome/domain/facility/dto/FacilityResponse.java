package com.careconnect.nursinghome.domain.facility.dto;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import lombok.Getter;

@Getter
public class FacilityResponse {

    private Long id;
    private String name;
    private String address;

    private Double latitude;
    private Double longitude;

    private String phoneNumber;

    private String grade;
    private String priceRange;

    private Boolean dementiaCare;
    private Boolean rehabilitation;

    private String operatingHours;
    private String introduction;

    public FacilityResponse(Facility facility) {
        this.id = facility.getId();
        this.name = facility.getName();
        this.address = facility.getAddress();
        this.latitude = facility.getLatitude();
        this.longitude = facility.getLongitude();
        this.phoneNumber = facility.getPhoneNumber();
        this.grade = facility.getGrade();
        this.priceRange = facility.getPriceRange();
        this.dementiaCare = facility.getDementiaCare();
        this.rehabilitation = facility.getRehabilitation();
        this.operatingHours = facility.getOperatingHours();
        this.introduction = facility.getIntroduction();
    }
}