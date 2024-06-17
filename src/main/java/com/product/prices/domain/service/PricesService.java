package com.product.prices.domain.service;

import com.product.prices.domain.model.BrandedProductPrice;

import java.time.LocalDateTime;

public interface PricesService {
    BrandedProductPrice productPriceOnDate(String brandId, String productId, LocalDateTime dateApplied);
}
