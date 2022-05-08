package com.commerce.shop.dto;


import com.commerce.shop.constant.ItemSellStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int cnt;
    private String detailDesc;
    private ItemSellStatus itemSellStatus;
    private LocalDateTime regTime;

}
