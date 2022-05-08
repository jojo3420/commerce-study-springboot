package com.commerce.shop.repository;

import com.commerce.shop.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findByEmail(String email);
    Optional<MemberEntity> findById(Long id);



}
