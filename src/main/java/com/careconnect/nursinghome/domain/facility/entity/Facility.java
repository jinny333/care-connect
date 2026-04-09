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

    // 추가된 필드
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

    public Facility(String name, String address, Double latitude, Double longitude,
                    String phoneNumber, String grade, String priceRange,
                    Boolean dementiaCare, Boolean rehabilitation,
                    String operatingHours, String introduction,
                    String staff, Integer capacity, String programs,
                    String openDate, Integer floors, Integer currentMale,
                    Integer currentFemale, Integer waitingMale,
                    Integer waitingFemale, String directions) {
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
        this.staff = staff;
        this.capacity = capacity;
        this.programs = programs;
        this.openDate = openDate;
        this.floors = floors;
        this.currentMale = currentMale;
        this.currentFemale = currentFemale;
        this.waitingMale = waitingMale;
        this.waitingFemale = waitingFemale;
        this.directions = directions;
    }

    public void update(String name, String address, Double latitude, Double longitude,
                       String phoneNumber, String grade, String priceRange,
                       Boolean dementiaCare, Boolean rehabilitation,
                       String operatingHours, String introduction,
                       String staff, Integer capacity, String programs,
                       String openDate, Integer floors, Integer currentMale,
                       Integer currentFemale, Integer waitingMale,
                       Integer waitingFemale, String directions) {
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
        this.staff = staff;
        this.capacity = capacity;
        this.programs = programs;
        this.openDate = openDate;
        this.floors = floors;
        this.currentMale = currentMale;
        this.currentFemale = currentFemale;
        this.waitingMale = waitingMale;
        this.waitingFemale = waitingFemale;
        this.directions = directions;
    }
}