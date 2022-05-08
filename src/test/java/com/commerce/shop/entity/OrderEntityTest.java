package com.commerce.shop.entity;

import com.commerce.shop.constant.ItemSellStatus;
import com.commerce.shop.constant.Role;
import com.commerce.shop.repository.ItemRepository;
import com.commerce.shop.repository.MemberRepository;
import com.commerce.shop.repository.OrderItemRepository;
import com.commerce.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = "classpath:application-test.properties")
@Transactional
class OrderEntityTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager entityManager;

    private ItemEntity createItem(int index) {
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setName("테스트 상품-" + index);
        itemEntity.setPrice(1000);
        itemEntity.setDetailDesc("상세설명-" + index);
        itemEntity.setItemSellStatus(ItemSellStatus.SELL);
        itemEntity.setCnt(10);
        itemEntity.setRegTime(LocalDateTime.now());
        itemEntity.setUpdateTime(LocalDateTime.now());
        return itemEntity;
    }

    private OrderEntity createOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderDate(LocalDateTime.now());

        for (int i = 0; i < 3; i++) {
            ItemEntity itemEntity = createItem(i);
            itemRepository.save(itemEntity);
            OrderItemEntity orderItemEntity = createOrderItemEntity(10 * i, 3000 * i, orderEntity, itemEntity);
            orderEntity.getOrderItemEntities().add(orderItemEntity);
        }
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setName("park");
        memberEntity.setAddress("adress");
        memberEntity.setEmail("test@email.com");
        memberEntity.setPw("12341234!@");
        memberEntity.setRole(Role.USER);
        memberRepository.save(memberEntity);

        orderEntity.setMemberEntity(memberEntity);
        orderRepository.save(orderEntity);

        return orderEntity;
    }

    @Test
    @DisplayName("영속성 전이 ALL 옵션 테스트")
    void OrderEntityTest() {
        // given
        int loopCnt = 5;
        OrderEntity orderEntity = new OrderEntity();  // 주문
        for (int i = 0; i < loopCnt; i++) {
            ItemEntity itemEntity = this.createItem(i);  // 아이템(상품)
            itemRepository.save(itemEntity);

            OrderItemEntity orderItemEntity = createOrderItemEntity(10, 3000, orderEntity, itemEntity);
            orderEntity.getOrderItemEntities().add(orderItemEntity);
            // orderItemEntity 은 저장하지 않았다.
        }
        // orderEntity 만 저장했음.
        orderRepository.saveAndFlush(orderEntity);
        entityManager.clear();
        // when

        OrderEntity savedOrderEntity = orderRepository.findById(orderEntity.getId())
                .orElseThrow(EntityNotFoundException::new);

        // then
        assertEquals(loopCnt, savedOrderEntity.getOrderItemEntities().size());
    }

    private OrderItemEntity createOrderItemEntity(int count, int orderPrice, OrderEntity orderEntity, ItemEntity itemEntity) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();  // 주문 아이템(상품)
        orderItemEntity.setCount(count);
        orderItemEntity.setOrderPrice(orderPrice);
        orderItemEntity.setOrderEntity(orderEntity);
        orderItemEntity.setItemEntity(itemEntity);
        return orderItemEntity;
    }


    @Test
    @DisplayName("고아 객체 제거 테스트")
    void orphanRemoveTest() {
        // given

        OrderEntity orderEntity = createOrderEntity();
        assertEquals(3, orderEntity.getOrderItemEntities().size());

        // when
        orderEntity.getOrderItemEntities().remove(0);
        entityManager.flush();


        // then
        assertEquals(2, orderEntity.getOrderItemEntities().size());
        // 자바에서 .remove() 메서드로 리스트에서 삭제하니 DB에서도 삭제가 발생함!
    }


    @Test
    @DisplayName("지연 로딩 테스트: 관계로 묶여있는 entity 간에 조회를 실제 사용할떄 조회함. )성능이슈해결)")
    void lazyLoadingTest() {
        OrderEntity orderEntity = createOrderEntity();
        Long orderItemId = orderEntity.getOrderItemEntities().get(0).getId();

        assertNotNull(orderItemId);


        entityManager.flush();
        entityManager.clear();

        OrderItemEntity orderItemEntity = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
        System.out.println(orderItemEntity.getClass());
        assertEquals(OrderItemEntity.class, orderItemEntity.getClass());


        System.out.println("================");
        System.out.println(orderItemEntity.getOrderEntity().getOrderDate());
        System.out.println("================");



    }
}