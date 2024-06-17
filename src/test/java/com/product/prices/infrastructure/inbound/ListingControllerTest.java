package com.product.prices.infrastructure.inbound;

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

    private static final String TEST_BRAND = "1";
    private static final String TEST_PRODUCT = "35455";

    private static final ExpectedOutput PRICE_LIST_ONE = new ExpectedOutput(
            TEST_PRODUCT,
            TEST_BRAND,
            "1",
            "2020-06-14T00:00:00",
            "2020-12-31T23:59:59",
            "EUR",
            35.5
    );

    private static final ExpectedOutput PRICE_LIST_TWO = new ExpectedOutput(
            TEST_PRODUCT,
            TEST_BRAND,
            "2",
            "2020-06-14T15:00:00",
            "2020-06-14T18:30:00",
            "EUR",
            25.45
    );

    private static final ExpectedOutput PRICE_LIST_THREE = new ExpectedOutput(
            TEST_PRODUCT,
            TEST_BRAND,
            "3",
            "2020-06-15T00:00:00",
            "2020-06-15T11:00:00",
            "EUR",
            30.5
    );

    private static final ExpectedOutput PRICE_LIST_FOUR = new ExpectedOutput(
            TEST_PRODUCT,
            TEST_BRAND,
            "4",
            "2020-06-15T16:00:00",
            "2020-12-31T23:59:59",
            "EUR",
            38.95
    );

    public static Stream<Arguments> provideInputForPriceCheck() {
        return Stream.of(
                Arguments.of(
                        new Input(
                                TEST_BRAND,
                                TEST_PRODUCT,
                                LocalDateTime.of(2020, 6, 14, 10, 0).toString()
                        ),
                        PRICE_LIST_ONE
                ),
                Arguments.of(
                        new Input(
                                TEST_BRAND,
                                TEST_PRODUCT,
                                LocalDateTime.of(2020, 6, 14, 16, 0).toString()
                        ),
                        PRICE_LIST_TWO
                ),
                Arguments.of(
                        new Input(
                                TEST_BRAND,
                                TEST_PRODUCT,
                                LocalDateTime.of(2020, 6, 14, 21, 0).toString()
                        ),
                        PRICE_LIST_ONE
                ),
                Arguments.of(
                        new Input(
                                TEST_BRAND,
                                TEST_PRODUCT,
                                LocalDateTime.of(2020, 6, 15, 10, 0).toString()
                        ),
                        PRICE_LIST_THREE
                ),
                Arguments.of(
                        new Input(
                                TEST_BRAND,
                                TEST_PRODUCT,
                                LocalDateTime.of(2020, 6, 16, 21, 0).toString()
                        ),
                        PRICE_LIST_FOUR
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

    @Test
    @DisplayName("BrandId should be present")
    void itShouldRaiseAnErrorOnBrandIdNotPresent() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("BrandId should be valid")
    void itShouldRaiseAnErrorOnBrandIdNotValid() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", "invalid_brand"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Product should be present")
    void itShouldRaiseAnErrorOnProductIdNotPresent() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", TEST_BRAND))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Product should be valid")
    void itShouldRaiseAnErrorOnProductIdNotValid() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", TEST_BRAND)
                        .queryParam("productId", "invalid_product"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Date applied should be present")
    void itShouldRaiseAnErrorOnDateAppliedNotPresent() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", TEST_BRAND)
                        .queryParam("productId", TEST_PRODUCT))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Date applied should be valid")
    void itShouldRaiseAnErrorOnDateAppliedNotValid() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", TEST_BRAND)
                        .queryParam("productId", TEST_PRODUCT)
                        .queryParam("dateApplied", "invalid_date"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Not found on valid input but no match")
    void itShouldRaiseAnErrorOnValidInputButNoMatch() throws Exception {
        mvc.perform(get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", TEST_BRAND)
                        .queryParam("productId", "1")
                        .queryParam("dateApplied", LocalDateTime.of(2020, 6, 14, 10, 0).toString()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
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
