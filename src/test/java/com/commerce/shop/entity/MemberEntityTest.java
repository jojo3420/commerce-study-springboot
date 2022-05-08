package com.commerce.shop.entity;

import com.commerce.shop.constant.Role;
import com.commerce.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "classpath:application-test.properties")
@Transactional
class MemberEntityTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;


    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username="gildong", roles="ADMIN")
    void memberTableAuditingTest() {

        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("test");
        memberEntity.setEmail("test@test.com");
        memberEntity.setPw("1234");
        memberEntity.setAddress("address");
        memberEntity.setRole(Role.USER);
        memberRepository.save(memberEntity);

        entityManager.flush();
        entityManager.clear();

        MemberEntity memberEntity1 = memberRepository.findById(memberEntity.getId()).orElseThrow(EntityNotFoundException::new);

        assertEquals(memberEntity.getName(), memberEntity1.getName());
        assertEquals("gildong", memberEntity1.getCreatedBy());
        assertEquals("gildong", memberEntity1.getModifiedBy());
        System.out.println(memberEntity1.getRegTime());
        System.out.println(memberEntity1.getUpdateTime());



    }
}