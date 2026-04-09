package com.careconnect.nursinghome.domain.member.service;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.dto.MemberResponseDto;
import com.careconnect.nursinghome.domain.member.dto.MemberUpdateDto;
import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.entity.Role;
import com.careconnect.nursinghome.domain.member.repository.MemberRepository;
import com.careconnect.nursinghome.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public MemberJoinResponse join(MemberJoinRequest dto) {
        // 1. 중복 이메일 검증
        validateDuplicateMember(dto.getEmail());

        // 🌟 이 줄이 핵심입니다! 입력받은 비번을 암호화(외계어 생성) 해요.
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        // 2. Entity 변환 (비밀번호 자리에 encodedPassword를 넣어야 함!)
        Member member = Member.builder()
                .email(dto.getEmail())
                .password(encodedPassword) // 👈 dto.getPassword() 대신 이걸 넣으세요!
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .role(dto.getRole() != null ? dto.getRole() : Role.CUSTOMER)
                .birthDate(dto.getBirthDate())
                .build();

        Member savedMember = memberRepository.save(member);

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

    @Transactional(readOnly = true)
    public MemberResponseDto getMyInfo(Long memberId) {
        return memberRepository.findById(memberId)
                .map(member -> MemberResponseDto.builder()
                        .email(member.getEmail())
                        .name(member.getName())
                        .phoneNumber(member.getPhoneNumber())
                        .role(member.getRole())
                        .birthDate(member.getBirthDate())
                        .build())
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public Long updateMyInfo(Long memberId, MemberUpdateDto updateDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        // 엔티티의 update 메서드 실행
        member.update(updateDto.getName(), updateDto.getPhoneNumber());

        return member.getId();
    }

    @Transactional
    public Long signup(MemberJoinRequest request) {
        // 비밀번호 암호화!!!
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member member = Member.builder()
                .email(request.getEmail())
                .password(encodedPassword) // 암호화된 비번 저장
                .name(request.getName())
                .role(Role.CUSTOMER) // 기본 역할 설정
                .build();

        return memberRepository.save(member).getId();
    }

    @Transactional
    public void withdrawMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));

        // 1. 상태를 탈퇴로 변경
        member.setStatus(Member.MemberStatus.WITHDRAWN);

        // 2. 탈퇴 시점을 현재 시간으로 기록
        member.setWithdrawnAt(LocalDateTime.now());
   }

    // MemberService.java

    public void logout(String accessToken) {
        // 1. 토큰 유효성 검사 (실무 필수!)
        if (!jwtTokenProvider.validateToken(accessToken)) {
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }

        // 2. 토큰의 남은 유효 시간 계산
        long expiration = jwtTokenProvider.getExpiration(accessToken);

        // 3. [실무 로직] Redis 블랙리스트 등록 (임시 주석 처리)
    /* redisTemplate.opsForValue().set(
            accessToken,
            "logout",
            expiration,
            TimeUnit.MILLISECONDS
    );
    */

        // 4. 테스트를 위해 로그는 남겨두기
        System.out.println("로그아웃 요청 - 블랙리스트 예약 토큰: " + accessToken);
        System.out.println("남은 유효 시간(ms): " + expiration);
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponseDto::from) // ⭐ 이 한 줄로 끝내세요! (제일 깔끔)
                .collect(Collectors.toList());
    }

    @Transactional
    public void withdrawMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 회원을 찾을 수 없습니다."));
        member.withdraw(); // 엔티티에 탈퇴 로직(isDeleted = true 등)이 있다면 실행
        memberRepository.save(member);
    }
}