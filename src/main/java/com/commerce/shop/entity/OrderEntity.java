package com.commerce.shop.entity;

import com.commerce.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    private LocalDateTime orderDate; // 주문일

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태


    @OneToMany(mappedBy = "orderEntity",
            cascade = CascadeType.ALL,  // ALL: 부모 엔티티의 영송성 상태 변화를 자식 엔티티에 모두 전이 시킴
            orphanRemoval = true, // 고아 객체 제거 옵션
            fetch = FetchType.LAZY
    )
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();


}
