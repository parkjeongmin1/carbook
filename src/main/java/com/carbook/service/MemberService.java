package com.carbook.service;

import com.carbook.config.MemberContext;
import com.carbook.entity.Member;
import com.carbook.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member); //회원정보 insert후 해당 회원정보 다시 리턴
    }

    //회원 중복체크
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }

    //중복확인
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Member member = memberRepository.findByEmail(email);
//
//        if(member == null) {
//            throw new UsernameNotFoundException(email);
//        }
//
//        return User.builder()
//                .username(member.getEmail())
//                .password(member.getPassword())
//                .roles(member.getRole().toString())
//                .build();
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //사용자가 입력한 email이 DB에 있는지 쿼리문을 사용한다.
        Member member = memberRepository.findByEmail(email);

        if(member == null) { //사용자가 없다면
            throw new UsernameNotFoundException(email);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("ADMIN".equals(member.getRole().toString())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new MemberContext(member, authorities);

    }
}
