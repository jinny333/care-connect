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
    private String staff;
    private Integer capacity;
    private String programs;
    private String openDate;
    private Integer floors;
    private Integer currentMale;
    private Integer currentFemale;
    private Integer waitingMale;
    private Integer waitingFemale;
    private String directions;

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
        this.staff = facility.getStaff();
        this.capacity = facility.getCapacity();
        this.programs = facility.getPrograms();
        this.openDate = facility.getOpenDate();
        this.floors = facility.getFloors();
        this.currentMale = facility.getCurrentMale();
        this.currentFemale = facility.getCurrentFemale();
        this.waitingMale = facility.getWaitingMale();
        this.waitingFemale = facility.getWaitingFemale();
        this.directions = facility.getDirections();
    }
}