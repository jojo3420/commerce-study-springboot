package com.commerce.shop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/")
    public String helloWorld() {
        return "hello world";
    }

    @GetMapping(value="/user")
    public MemberDto getMember() {
        MemberDto memberDto = new MemberDto();
        memberDto.setName("TEST");
        memberDto.setAge(20);
        return memberDto;
    }



}
