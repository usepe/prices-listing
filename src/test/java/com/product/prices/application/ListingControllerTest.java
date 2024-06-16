package com.product.prices.application;

import com.product.prices.PricesListingsApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PricesListingsApplication.class)
@AutoConfigureMockMvc
public class ListingControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test 1: petición a las 10:00 del día 2020/06/14 del producto 35455 para la brand 1")
    void itShouldReturnDataGivenCertainInputs() throws Exception {
        var dateApplied = LocalDateTime.of(2020, 6, 14, 10, 0);
        var productId = "35455";
        var brandId = "1";

        mvc.perform(get("/prices")
                .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("brandId", brandId)
                        .queryParam("productId", productId)
                        .queryParam("dateApplied", dateApplied.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value("35455"))
                .andExpect(jsonPath("$.brandId").value("1"))
                .andExpect(jsonPath("$.priceList").value("1"))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T00:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-12-31T23:59:59"))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.price").value(35.5));
    }
}
