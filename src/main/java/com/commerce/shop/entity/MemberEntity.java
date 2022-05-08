package com.commerce.shop.entity;

import com.commerce.shop.constant.Role;
import com.commerce.shop.dto.MemberJoinDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;


@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "pw", nullable = false)
    private String pw;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;


    public static MemberEntity createMember(MemberJoinDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity member = new MemberEntity();
        member.setEmail(memberDto.getEmail());
        if (!memberDto.getPw1().equals(memberDto.getPw2())) {
            throw new IllegalStateException("두 패스워드가 일치하지 않습니다.");
        }
        member.setPw(passwordEncoder.encode(memberDto.getPw1()));
        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());
        member.setRole(Role.USER);

        return member;
    }

    public static MemberEntity createAdmin(MemberJoinDto memberDto, PasswordEncoder passwordEncoder) {
        MemberEntity member = new MemberEntity();
        member.setEmail(memberDto.getEmail());
        if (!memberDto.getPw1().equals(memberDto.getPw2())) {
            throw new IllegalStateException("두 패스워드가 일치하지 않습니다.");
        }
        member.setPw(passwordEncoder.encode(memberDto.getPw1()));
        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());
        member.setRole(Role.ADMIN);

        return member;
    }
}
