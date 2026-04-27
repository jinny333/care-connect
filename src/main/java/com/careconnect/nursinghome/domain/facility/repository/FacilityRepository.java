package com.careconnect.nursinghome.domain.facility.repository;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    // 키워드 검색
    @Query("SELECT f FROM Facility f WHERE f.name LIKE %:keyword% OR f.address LIKE %:keyword%")
    List<Facility> findByKeyword(@Param("keyword") String keyword);

    // 위치 기반 검색 (Haversine 공식)
    @Query(value = """
            SELECT * FROM facility
            WHERE (6371 * acos(
                cos(radians(:lat)) * cos(radians(latitude))
                * cos(radians(longitude) - radians(:lon))
                + sin(radians(:lat)) * sin(radians(latitude))
            )) <= :radius
            """, nativeQuery = true)
    List<Facility> findByLocation(
            @Param("lat") Double lat,
            @Param("lon") Double lon,
            @Param("radius") Double radius
    );
}