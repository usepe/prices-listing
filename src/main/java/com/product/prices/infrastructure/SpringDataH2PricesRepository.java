package com.product.prices.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataH2PricesRepository extends JpaRepository<BrandedProductPrice, Long> {
    @Query(value = """
            SELECT id,
                brand_id,
                start_date,
                end_date,
                price_list,
                product_id,
                priority,
                price,
                currency
            FROM prices
            WHERE brand_id = :brandId
            AND product_id = :productId
            AND :dateApplied BETWEEN start_date AND end_date
            """, nativeQuery = true)
    List<BrandedProductPrice> findByPriceApplied(@Param("brandId") String brandId,
                                                 @Param("productId") String productId,
                                                 @Param("dateApplied") LocalDateTime dateApplied);
}
