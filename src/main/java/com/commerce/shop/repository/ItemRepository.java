package com.commerce.shop.repository;

import com.commerce.shop.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByName(String name);

    List<ItemEntity> findByNameOrDetailDesc(String name, String detailDesc);

    List<ItemEntity> findByPriceLessThan(Integer price);

    List<ItemEntity> findByPriceLessThanOrderByPriceDesc(Integer price);

    /**
     * JPQL를 통해 쿼리문 생성: entity 기반으로 쿼리문 작성으로 DB 의존성 제거되어 독립적이므로 유지보수성 증가
     *
     * @param detailDesc
     * @return
     */
    @Query("SELECT item FROM ItemEntity item WHERE item.detailDesc LIKE %:detailDesc% " +
            "ORDER BY item.price DESC")
    List<ItemEntity> findByDetailWithJPQL(@Param("detailDesc") String detailDesc);

    /**
     * native SQL 문으로 실행시켜 보기
     * @param detailDesc
     * @return
     */
    @Query(value = "SELECT * FROM item " +
            "WHERE detail_desc LIKE %:detailDesc%  ORDER BY price DESC LIMIT 5",
            nativeQuery = true)
    List<ItemEntity> findByDetailDescWithNative(@Param("detailDesc") String detailDesc);

}
