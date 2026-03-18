package com.careconnect.nursinghome.domain.member.service;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberJoinResponse join(MemberJoinRequest dto) {
        // 1. 중복 이메일 검증
        validateDuplicateMember(dto.getEmail());

        // 2. DTO -> Entity 변환 (택배 상자 까서 DB 저장용으로 바꾸기)
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole())
                .birthDate(dto.getBirthDate())
                .build();

        Member savedMember = memberRepository.save(member);

        // 3. Response DTO로 반환 (나가는 상자에 담기)
        return new MemberJoinResponse(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getName()
        );
    }

    private void validateDuplicateMember(String email) {
        memberRepository.findByEmail(email)
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}