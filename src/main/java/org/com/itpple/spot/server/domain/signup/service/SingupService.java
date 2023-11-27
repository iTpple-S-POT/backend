package org.com.itpple.spot.server.domain.signup.service;

import lombok.RequiredArgsConstructor;
import org.com.itpple.spot.server.domain.member.entity.Member;
import org.com.itpple.spot.server.domain.member.repository.MemberRepository;
import org.com.itpple.spot.server.domain.signup.request.MemberRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SingupService {
    private final MemberRepository memberRepository;

    @Transactional
    public void joinMember(MemberRequestDto requestDto) {
        Member member = requestDto.toMember();
        memberRepository.save(member);
    }


    public boolean findMemberByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

}
