package com.product.prices.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataH2PricesRepository extends JpaRepository<BrandedProductPrice, Long> {
    List<BrandedProductPrice> findByBrandIdAndProductId(String brandId, String productId);
}
