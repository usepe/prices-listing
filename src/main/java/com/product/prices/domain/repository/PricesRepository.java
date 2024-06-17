package com.product.prices.domain.repository;

import com.product.prices.domain.model.BrandedProductPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesRepository {
    List<BrandedProductPrice> listProductPrices(String brandId, String productId, LocalDateTime dateApplied);
}
