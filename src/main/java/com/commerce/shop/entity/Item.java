package com.commerce.shop.entity;

import com.commerce.shop.constant.ItemSellStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int cnt;

    @Lob
    @Column(nullable = false)
    private String detailDesc;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ItemSellStatus itemSellStatus;


    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
