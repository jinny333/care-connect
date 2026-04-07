package com.careconnect.nursinghome.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 공통 매핑 정보가 필요할 때 쓰는 어노테이션!
@EntityListeners(AuditingEntityListener.class) // 자동으로 날짜 채워주는 기능 활성화
public abstract class BaseTimeEntity {

    @CreatedDate
    private LocalDateTime createdDate; // 생성일

    @LastModifiedDate
    private LocalDateTime lastModifiedDate; // 수정일
}
