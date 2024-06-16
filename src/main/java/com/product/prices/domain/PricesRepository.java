package com.product.prices.domain;

import java.util.List;

public interface PricesRepository {
    List<BrandedProductPrice> listProductPrices(String brandId, String productId);
}
