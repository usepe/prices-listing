package com.product.prices.application;

import com.product.prices.PricesListingsApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PricesListingsApplication.class)
@AutoConfigureMockMvc
public class ListingControllerTest {
    @Autowired
    private MockMvc mvc;

    public static Stream<Arguments> provideInputForPriceCheck() {
        return Stream.of(
                Arguments.of(
                        new Input(
                                "1",
                                "35455",
                                LocalDateTime.of(2020, 6, 14, 10, 0).toString()
                        ),
                        new ExpectedOutput(
                                "35455",
                                "1",
                                "1",
                                "2020-06-14T00:00:00",
                                "2020-12-31T23:59:59",
                                "EUR",
                                35.5
                        )
                ),
                Arguments.of(
                        new Input(
                                "1",
                                "35455",
                                LocalDateTime.of(2020, 6, 14, 16, 0).toString()
                        ),
                        new ExpectedOutput(
                                "35455",
                                "1",
                                "2",
                                "2020-06-14T15:00:00",
                                "2020-06-14T18:30:00",
                                "EUR",
                                25.45
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideInputForPriceCheck")
    @DisplayName("Tests different inputs")
    void itShouldReturnDataGivenCertainInputs(Input input,
                                              ExpectedOutput expected) throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", input.brandId)
                        .queryParam("productId", input.productId)
                        .queryParam("dateApplied", input.dateApplied))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(expected.productId))
                .andExpect(jsonPath("$.brandId").value(expected.brandId))
                .andExpect(jsonPath("$.priceList").value(expected.priceList))
                .andExpect(jsonPath("$.startDate").value(expected.startDate))
                .andExpect(jsonPath("$.endDate").value(expected.endDate))
                .andExpect(jsonPath("$.currency").value(expected.currency))
                .andExpect(jsonPath("$.price").value(expected.price));
    }

    record Input(String brandId,
                 String productId,
                 String dateApplied) {
        @Override
        public String toString() {
            return "Petición de precio de producto " + productId + " de la brand " + brandId + " en fecha " + dateApplied;
        }
    }

    record ExpectedOutput(
            String productId,
            String brandId,
            String priceList,
            String startDate,
            String endDate,
            String currency,
            Double price
    ) {
        @Override
        public String toString() {
            return "Lista " + priceList + " en " + currency + " " + price;
        }
    }
}
