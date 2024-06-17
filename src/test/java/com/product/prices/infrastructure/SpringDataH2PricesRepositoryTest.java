package com.product.prices.infrastructure;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class SpringDataH2PricesRepositoryTest {
    @Autowired
    private SpringDataH2PricesRepository pricesRepository;

    public static Stream<Arguments> provide() {
        return Stream.of(
                Arguments.of(
                        new Input("0", "0", LocalDateTime.MIN),
                        List.of()
                ),
                Arguments.of(
                        new Input("1", "0", LocalDateTime.MIN),
                        List.of()
                ),
                Arguments.of(
                        new Input("1", "35455", LocalDateTime.MIN),
                        List.of()
                ),
                Arguments.of(
                        new Input("1", "35455", LocalDateTime.of(2020, 6, 14, 0, 0)),
                        List.of(new BrandedProductPrice(
                                1,
                                "1",
                                "35455",
                                "1",
                                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                                "EUR",
                                new BigDecimal("35.50"),
                                0
                        ))
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provide")
    void shouldReturnElementsByGivenParameters(Input input, List<BrandedProductPrice> expected) {
        var actual = pricesRepository.findByPriceApplied(input.brandId, input.productId, input.dateApplied);

        assertEquals(expected, actual);
    }

    record Input(String brandId, String productId, LocalDateTime dateApplied) {
    }
}