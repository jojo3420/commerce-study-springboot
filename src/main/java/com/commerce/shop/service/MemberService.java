package com.commerce.shop.service;

import com.commerce.shop.dto.MemberJoinDto;
import com.commerce.shop.entity.MemberEntity;
import com.commerce.shop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;


    private void validationDuplicationMember(MemberJoinDto memberDto) {
        MemberEntity findEntity = memberRepository.findByEmail(memberDto.getEmail());
        if (findEntity != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

//        if (!memberDto.getPw1().equals(memberDto.getPw2())) {
//            throw new IllegalStateException("두 패스워드가 일치하지 않습니다.");
//        }
    }

    public MemberEntity joinMember(MemberJoinDto memberDto,  PasswordEncoder passwordEncoder) throws IllegalStateException {
        validationDuplicationMember(memberDto);
        MemberEntity memberEntity = MemberEntity.createMember(memberDto, passwordEncoder);
        return memberRepository.save(memberEntity);

    }

    /**
     * Spring Security 회원 정보 조회
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(email);

        if (memberEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(email)
                .password(memberEntity.getPw())
                .roles(memberEntity.getRole().toString())
                .build();
    }

}
