package com.product.prices.infrastructure;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class H2PricesRepositoryTest {
    private SpringDataH2PricesRepository springDataH2PricesRepository = mock(SpringDataH2PricesRepository.class);

    private H2PricesRepository repository = new H2PricesRepository(springDataH2PricesRepository);

    @Test
    void itShouldMapTheProductProperly() {
        when(springDataH2PricesRepository.findByBrandIdAndProductId("1", "1"))
                .thenReturn(List.of(new BrandedProductPrice(
                        1,
                        "1",
                        "1",
                        "1",
                        LocalDateTime.of(2020, 8, 26, 0, 0),
                        LocalDateTime.of(2020, 8, 26, 23, 59),
                        "EUR",
                        BigDecimal.ONE,
                        0
                )));

        var actual = repository.listProductPrices("1", "1");

        var expected = List.of(new com.product.prices.domain.BrandedProductPrice(
                "1",
                "1",
                "1",
                LocalDateTime.of(2020, 8, 26, 0, 0),
                LocalDateTime.of(2020, 8, 26, 23, 59),
                "EUR",
                BigDecimal.ONE,
                0
        ));

        assertEquals(expected, actual);
    }
}