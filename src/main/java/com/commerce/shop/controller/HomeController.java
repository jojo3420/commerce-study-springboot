package com.commerce.shop.controller;

import com.commerce.shop.constant.ItemSellStatus;
import com.commerce.shop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping(value = "/")
    public String home() {
        return "thymeleaf/test-layout-content";
    }

    @GetMapping(value = "/thymeleaf/ex01")
    public String ex01(Model model) {
        model.addAttribute("data", "타임리프 예제입니다!");
        return "thymeleaf/ex01-th-text";
    }

    @GetMapping(value = "/thymeleaf/ex02")
    public String ex02(Model model) {
        ItemDto itemDto = new ItemDto(1L, "사과", 1000, 10, "싱싱한 부영 사과", ItemSellStatus.SELL, LocalDateTime.now());
        model.addAttribute("itemDto", itemDto);
        return "thymeleaf/ex02-dto-binding";
    }

    @GetMapping(value = "/thymeleaf/ex03")
    public String ex03(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemDto itemDto = new ItemDto(new Long(i*i), "사과" + i, 1000 * i, 10 * i, "싱싱한 부영 사과", ItemSellStatus.SELL, LocalDateTime.now());
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/ex03-th-each";
    }

    @GetMapping(value = "/thymeleaf/ex04")
    public String ex04(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemDto itemDto = new ItemDto(new Long(i*i), "사과" + i, 1000 * i, 10 * i, "싱싱한 부영 사과", ItemSellStatus.SELL, LocalDateTime.now());
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/ex04-th-if-unless";
    }

    @GetMapping(value = "/thymeleaf/ex05")
    public String ex05(Model model) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ItemDto itemDto = new ItemDto(new Long(i*i), "사과" + i, 1000 * i, 10 * i, "싱싱한 부영 사과", ItemSellStatus.SELL, LocalDateTime.now());
            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleaf/ex05-th-switch";
    }

    @GetMapping(value = "/thymeleaf/ex06")
    public String ex06() {
        return "thymeleaf/ex06-th-href";
    }
    @GetMapping(value = "/thymeleaf/ex07")
    public String ex07(String param1, String param2, Model model) {
        model.addAttribute("param1" , param1);
        model.addAttribute("param2" , param2);
        return "thymeleaf/ex07-pass-params";
    }



}
