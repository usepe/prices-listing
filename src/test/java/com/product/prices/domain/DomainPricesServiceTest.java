package com.product.prices.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DomainPricesServiceTest {
    private final PricesRepository repository = mock(PricesRepository.class);

    private final DomainPricesService service = new DomainPricesService(repository);

    @Test
    void shouldReturnPriceWithHigherPriorityForAGivenProduct() {
        var productOne = new BrandedProductPrice("1", "1", "1",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 6, 15, 0, 0),
                "EUR", BigDecimal.ONE, 0);

        var productTwo = new BrandedProductPrice("1", "1", "1",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 6, 15, 0, 0),
                "EUR", BigDecimal.TEN, 1);

        var listOfProducts = List.of(productOne, productTwo);

        when(repository.listProductPrices("1", "1")).thenReturn(listOfProducts);

        var brandId = "1";
        var productId = "1";
        var dateApplied = LocalDateTime.of(2020, 6, 14, 10, 0);

        var actual = service.productPriceOnDate(brandId, productId, dateApplied);

        assertEquals(productTwo, actual);
    }

    @Test
    void shouldReturnOnlyProductPricesThatAreBetweenTheGivenDate() {
        var productOne = new BrandedProductPrice("1", "1", "1",
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 6, 16, 0, 0),
                "EUR", BigDecimal.ONE, 0);

        var productTwo = new BrandedProductPrice("1", "1", "1",
                LocalDateTime.of(2020, 6, 17, 0, 0),
                LocalDateTime.of(2020, 6, 18, 0, 0),
                "EUR", BigDecimal.TEN, 1);

        var listOfProducts = List.of(productOne, productTwo);

        when(repository.listProductPrices("1", "1")).thenReturn(listOfProducts);

        var brandId = "1";
        var productId = "1";
        var dateApplied = LocalDateTime.of(2020, 6, 14, 10, 0);

        var actual = service.productPriceOnDate(brandId, productId, dateApplied);

        assertEquals(productOne, actual);
    }
}