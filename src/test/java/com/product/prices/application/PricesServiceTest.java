package com.product.prices.application;

import com.product.prices.domain.exception.PriceNotFoundException;
import com.product.prices.domain.model.BrandedProductPrice;
import com.product.prices.domain.repository.PricesRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PricesServiceTest {
    private final PricesRepository repository = mock(PricesRepository.class);

    private final com.product.prices.application.PricesService service = new com.product.prices.application.PricesService(repository);

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

        var brandId = "1";
        var productId = "1";
        var dateApplied = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(repository.listProductPrices(brandId, productId, dateApplied)).thenReturn(listOfProducts);

        var actual = service.productPriceOnDate(brandId, productId, dateApplied);

        assertEquals(productTwo, actual);
    }

    @Test
    void shouldThrowAnExceptionWhenCannotFindAProductPrice() {
        var listOfProducts = List.<BrandedProductPrice>of();

        var brandId = "1";
        var productId = "1";
        var dateApplied = LocalDateTime.of(2020, 6, 14, 10, 0);

        when(repository.listProductPrices(brandId, productId, dateApplied)).thenReturn(listOfProducts);

        assertThrows(PriceNotFoundException.class, () -> service.productPriceOnDate(brandId, productId, dateApplied));
    }
}