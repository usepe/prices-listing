package com.product.prices.application;

import com.product.prices.domain.BrandedProductPrice;
import com.product.prices.domain.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class ListingController {
    private final PricesService service;

    @Autowired
    public ListingController(PricesService service) {
        this.service = service;
    }

    @GetMapping("/prices")
    public BrandedProductPrice price(String brandId, String productId, LocalDateTime dateApplied) {
        return BrandedProductPrice.of(service.productPriceOnDate(brandId, productId, dateApplied));
    }

    public record BrandedProductPrice(
            String brandId,
            String productId,
            String priceList,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String currency,
            BigDecimal price
    ) {
        public static BrandedProductPrice of(com.product.prices.domain.BrandedProductPrice brandedProductPrice) {
            return new BrandedProductPrice(
                    brandedProductPrice.brandId(),
                    brandedProductPrice.productId(),
                    brandedProductPrice.priceList(),
                    brandedProductPrice.startDate(),
                    brandedProductPrice.endDate(),
                    brandedProductPrice.currency(),
                    brandedProductPrice.price()
            );
        }
    }
}
