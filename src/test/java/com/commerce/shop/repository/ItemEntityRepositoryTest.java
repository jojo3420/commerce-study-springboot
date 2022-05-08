package com.commerce.shop.repository;

import com.commerce.shop.constant.ItemSellStatus;
import com.commerce.shop.entity.ItemEntity;
import com.commerce.shop.entity.QItemEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "classpath:application-test.properties")
class ItemEntityRepositoryTest {

    @Autowired
    ItemRepository repository;

    @PersistenceContext
    EntityManager entityManager;


    @BeforeEach
    void setUp() {
        List<ItemEntity> list = repository.findAll();
        if (list.size() == 0) {
            for (int i = 0; i < 10; i++) {
                ItemEntity itemEntity = new ItemEntity();
                itemEntity.setName("상품" + i);
                itemEntity.setCnt(i * 10);
                itemEntity.setPrice(i * 1000);
                itemEntity.setRegTime(LocalDateTime.now());
                itemEntity.setDetailDesc("상품 상세 설명 " + i);
                itemEntity.setItemSellStatus(ItemSellStatus.SELL);
                if (i % 2 == 0) {
                    itemEntity.setItemSellStatus(ItemSellStatus.SOLD_OUT);
                    itemEntity.setCnt(0);
                }
                repository.save(itemEntity);
            }
        }
    }

    @Test
    @DisplayName("상품 아이템 저장 테스트")
    public void createItemTest() {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName("사과");
        itemEntity.setCnt(100);
        itemEntity.setItemSellStatus(ItemSellStatus.SELL);
        itemEntity.setPrice(3000);
        itemEntity.setDetailDesc("부영 애플 사과");
        itemEntity.setRegTime(LocalDateTime.now());
        itemEntity.setUpdateTime(LocalDateTime.now());
        ItemEntity savedItemEntity = repository.save(itemEntity);
        System.out.println(savedItemEntity.toString());
        assertEquals(itemEntity, savedItemEntity);
    }

//    @BeforeTestClass

    @Test
    @DisplayName("아이템 이름으로 찾기")
    public void findByNameTest() {
        String name = "상품2";
        List<ItemEntity> itemEntityList = repository.findByName(name);
        assertEquals(1, itemEntityList.size());
        assertEquals(name, itemEntityList.get(0).getName());
    }

    @Test
    @DisplayName("아이템 이름 OR 상세설명 으로 찾기")
    public void findByNameOrDetailDescTest() {
        String name = "상품2";
        String detailDesc = "상품 상세 설명 5";
        List<ItemEntity> allList = repository.findAll();
        assertEquals(10, allList.size());
        List<ItemEntity> itemEntityList = repository.findByNameOrDetailDesc(name, detailDesc);
        assertEquals(2, itemEntityList.size());
        assertEquals(name, itemEntityList.get(0).getName());
        assertEquals(detailDesc, itemEntityList.get(1).getDetailDesc());
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThanTest() {
        // 5000원 보다 낮은 가격만 조회
        List<ItemEntity> itemEntityList = repository.findByPriceLessThan(5000);
        assertTrue(itemEntityList.size() >= 0);
        for (ItemEntity itemEntity : itemEntityList) {
            assertTrue(5000 > itemEntity.getPrice());
        }
        List<ItemEntity> itemEntityList2 = repository.findByPriceLessThanOrderByPriceDesc(5000);
        for (ItemEntity itemEntity2 : itemEntityList2) {
            System.out.println(itemEntity2.toString());
        }
    }



    @Test
    @DisplayName("@Query 어노테이션 JPQL을 이용한 상품 조회")
    public void QueryAnnotationTest() {
        String detailDesc = "상품 상세";
        List<ItemEntity> itemEntityList = repository.findByDetailWithJPQL(detailDesc);
        for (ItemEntity itemEntity : itemEntityList) {
            System.out.println(itemEntity.toString());
        }
    }

    @Test
    @DisplayName("@Query 어노테이션 네이티브 SQL 사용")
    void nativeQueryAnnotationTest() {
        String detailDesc = "상품 상세";
        List<ItemEntity> itemEntityList = repository.findByDetailDescWithNative(detailDesc);
        assertEquals(5, itemEntityList.size());
        for (ItemEntity itemEntity : itemEntityList) {
            System.out.println(itemEntity.toString());
        }
    }
    public void beforeCreateData() {

    }
    @Test
    @DisplayName("Querydsl(java로 sql생성 api) 조회 테스트1")
    void queryDslTest() {
        JPAQueryFactory factory = new JPAQueryFactory(entityManager);
        QItemEntity qItem = QItemEntity.itemEntity;
        JPAQuery<ItemEntity> query = factory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.detailDesc.like("%상품 상세%" ))
                .orderBy(qItem.price.desc());

        List<ItemEntity> itemEntityList = query.fetch();

        for (ItemEntity itemEntity : itemEntityList) {
            System.out.println(itemEntity.toString());
        }
    }
}