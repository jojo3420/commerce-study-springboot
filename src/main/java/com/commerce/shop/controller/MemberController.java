package com.commerce.shop.controller;


import com.commerce.shop.dto.MemberJoinDto;
import com.commerce.shop.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/join")
    public String memberJoinPage(Model model) {
        model.addAttribute("memberJoinDto", new MemberJoinDto());
        return "member/join-member";

    }

    @PostMapping(value = "/join")
    public String memberJoin(@Valid MemberJoinDto dto, BindingResult bindingResult, Model model) {

        // DTO 검증 실패
        if (bindingResult.hasErrors()) {
            return "member/join-member";
        }

        try {
            memberService.joinMember(dto, passwordEncoder);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/join-member";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "member/login-form";
    }

    @GetMapping(value ="/login/failure")
    public String loginFailurePage(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "member/login-form";
    }

}
