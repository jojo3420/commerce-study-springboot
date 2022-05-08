package com.commerce.shop.repository;

import com.commerce.shop.dto.MemberJoinDto;
import com.commerce.shop.entity.MemberEntity;
import com.commerce.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest(properties = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class MemberRepositoryTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    public MemberJoinDto createMemberDto() {
        return new MemberJoinDto("test@test.com", "testpw1", "testpw1", "test user", "서울시 마포구 마포대로 12");
    }


    @Test
    @DisplayName("정상 회원 가입 테스트")
    void JoinMemberTest() {
        // given
        MemberJoinDto memberDto = createMemberDto();
        // when
        MemberEntity savedMember = memberService.joinMember(memberDto, passwordEncoder);
        // then
        assertEquals(memberDto.getEmail(), savedMember.getEmail());
        assertEquals(memberDto.getName(), savedMember.getName());
        assertEquals(memberDto.getAddress(), savedMember.getAddress());
        assertNotNull(savedMember.getId());


    }

    @Test
    @DisplayName("중복 이메일 회원가입 방지")
    void duplicationEmailTest() {
        // given
        MemberJoinDto memberDto1 = new MemberJoinDto("abc@test.com", "pw12341234", "pw12341234", "hello", "addr");
        memberService.joinMember(memberDto1, passwordEncoder);
        // when

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.joinMember(memberDto1, passwordEncoder);
        });

        // then
        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

    @Test
    @DisplayName("회원 가입시 입력한 패스워드1, 2 가 불일치")
    void PasswordMismatchTest() {
        // given
        MemberJoinDto dto1 = new MemberJoinDto("aaa@aaa.com", "pwmiss1234112", "pw12341234", "김철수", "서울시 강남구");

        // when
        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.joinMember(dto1, passwordEncoder);
        });

        // then
        assertEquals("두 패스워드가 일치하지 않습니다.", e.getMessage());
    }


    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() throws Exception {
        // given
        String email = "hello@test.com";
        String pw = "12341234";
        MemberJoinDto dto = new MemberJoinDto(email, pw, pw, "haha", "집주소");
        MemberEntity memberEntity = memberService.joinMember(dto, passwordEncoder);

        // when
        assertNotNull(memberEntity);

        // then
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/member/login")
                        .user(email).password(pw))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    void LoginFailedTest() throws Exception {
        // given
        String email = "hello@test.com";
        String pw = "12341234";
        MemberJoinDto dto = new MemberJoinDto(email, pw, pw, "haha", "집주소");
        MemberEntity memberEntity = memberService.joinMember(dto, passwordEncoder);

        // when
        assertNotNull(memberEntity);
        // then
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/member/login")
                        .user(email).password(pw + "!@#$"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }




}
