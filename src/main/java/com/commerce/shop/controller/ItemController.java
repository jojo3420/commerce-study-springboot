package com.commerce.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ItemController {

    @GetMapping("/item/new")
    public String registerItem() {
        return "item/item-form";
    }

}
