package com.commerce.shop.entity;

import com.commerce.shop.dto.MemberJoinDto;
import com.commerce.shop.repository.CartRepository;
import com.commerce.shop.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "classpath:application-test.properties")
@Transactional
class CartEntityTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUp() {
        System.out.println("setUp()");
    }


    @AfterEach
    void tearDown() {
        System.out.println("tearDown()");
    }


    public MemberEntity createMember() {
        MemberJoinDto dto = new MemberJoinDto("test@email.com", "pw12341234", "pw12341234", "spring", "주소");
        return MemberEntity.createMember(dto, passwordEncoder);
    }

    @Test
    @DisplayName("Cart- Member 1:1 단뱡향 관계 테스트")
    void Test() {
        // given
        MemberEntity memberEntity = createMember();
        memberRepository.save(memberEntity);

        // when
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMember(memberEntity);
        cartRepository.save(cartEntity);

        entityManager.flush();
        entityManager.clear(); // 테이터베이스 쿼리를 보기위해 persistence 영역 클리어

        CartEntity savedCartEntity = cartRepository.findById(cartEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertEquals(memberEntity.getId(), savedCartEntity.getMember().getId());


    }
    @Test
    @DisplayName("")
    void Test1() {
        // given

        // when

        // then
    }

}