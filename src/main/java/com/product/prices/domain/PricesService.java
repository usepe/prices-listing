package com.product.prices.domain;

import java.time.LocalDateTime;

public interface PricesService {
    BrandedProductPrice productPriceOnDate(String brandId, String productId, LocalDateTime dateApplied);
}
