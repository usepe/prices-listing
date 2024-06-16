package com.product.prices.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class ListingController {
    @GetMapping("/prices")
    public BrandedProductPrice price() {
        return new BrandedProductPrice("1", "35455", "1",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                "EUR",
                BigDecimal.valueOf(35.5));
    }

    public record BrandedProductPrice(
            String brandId,
            String productId,
            String priceList,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String currency,
            BigDecimal price
    ){}
}
