package com.careconnect.nursinghome.domain.facility.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 기관명
    private String address;

    private Double latitude;
    private Double longitude;

    private String phoneNumber;

    private String grade;
    private String priceRange;

    private Boolean dementiaCare;
    private Boolean rehabilitation;

    private String operatingHours; // 간단히 통합

    private String introduction; // 소개

    public Facility(String name, String address, Double latitude, Double longitude,
                    String phoneNumber, String grade, String priceRange,
                    Boolean dementiaCare, Boolean rehabilitation,
                    String operatingHours, String introduction) {

        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.priceRange = priceRange;
        this.dementiaCare = dementiaCare;
        this.rehabilitation = rehabilitation;
        this.operatingHours = operatingHours;
        this.introduction = introduction;
    }

    public void update(String name, String address, Double latitude, Double longitude,
                       String phoneNumber, String grade, String priceRange,
                       Boolean dementiaCare, Boolean rehabilitation,
                       String operatingHours, String introduction) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.grade = grade;
        this.priceRange = priceRange;
        this.dementiaCare = dementiaCare;
        this.rehabilitation = rehabilitation;
        this.operatingHours = operatingHours;
        this.introduction = introduction;
    }
}