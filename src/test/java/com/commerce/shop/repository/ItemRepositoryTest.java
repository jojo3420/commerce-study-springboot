package com.commerce.shop.repository;

import com.commerce.shop.constant.ItemSellStatus;
import com.commerce.shop.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired
    ItemRepository repository;

    @Test
    @DisplayName("상품 아이템 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setName("사과");
        item.setCnt(100);
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setPrice(3000);
        item.setDetailDesc("부영 애플 사과");
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = repository.save(item);
        System.out.println(savedItem.toString());
        assertEquals(item, savedItem);
    }

}