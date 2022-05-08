package com.commerce.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class CartEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY) // 기본설정: 즉시 로딩 => OneToOne, ManyToOne의 기본설정임.
    @JoinColumn(name="member_id")
    private MemberEntity member;


}
