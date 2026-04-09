package com.careconnect.nursinghome.domain.facility.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FacilityRequest {

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
}