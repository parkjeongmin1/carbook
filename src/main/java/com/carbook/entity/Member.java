package com.carbook.entity;

import com.carbook.constant.Role;
import com.carbook.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends Base{
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //pk값의 타입은 참조 타입(객체) Long 으로 지정

    @Column(unique = true)
    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //패스워드 암호화
        String password = passwordEncoder.encode(memberFormDto.getPassword());

        Member member = new Member();

        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(password);
        member.setRole(Role.ADMIN); // 관리자로 가입할 때

        return member;
    }
}
