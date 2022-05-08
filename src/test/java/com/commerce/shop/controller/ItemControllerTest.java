package com.commerce.shop.controller;

import com.commerce.shop.constant.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(properties = "classpath:application-test.properties")
@AutoConfigureMockMvc
class ItemControllerTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("어드민 페이지 접근권한 테스트: 상품 등록 페이지")
    @WithMockUser(username="admin", roles="ADMIN")
    void pageAccessAdminRoleTest() throws Exception {
        String path = "/admin/item/new";
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("일반 유저 어드민 페이지 접근 방지 테스트")
    @WithMockUser(username = "user", roles="USER")
    void PageAccessUserRoleTest() throws Exception {
        // given
        String path = "/admin/item/new";

        // when
        mockMvc.perform(MockMvcRequestBuilders.get(path))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // then
    }
}