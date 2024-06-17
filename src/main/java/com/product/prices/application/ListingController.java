package com.product.prices.application;

import com.product.prices.domain.PricesService;
import com.product.prices.domain.exception.PriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@Validated
public class ListingController {
    private final PricesService service;

    @Autowired
    public ListingController(PricesService service) {
        this.service = service;
    }

    @GetMapping("/prices")
    public BrandedProductPrice price(@NotBlank @Pattern(regexp="[0-9]+") String brandId,
                                     @NotBlank @Pattern(regexp="[0-9]+") String productId,
                                     @NotNull LocalDateTime dateApplied) {
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

    @ControllerAdvice
    class ListingControllerExceptionHandler {
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(value = { ConstraintViolationException.class, MethodArgumentTypeMismatchException.class })
        public @ResponseBody ErrorInfo handleInvalidInput(HttpServletRequest req, Exception ex) {
            return new ErrorInfo(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        }

        @ResponseStatus(HttpStatus.NOT_FOUND)
        @ExceptionHandler(PriceNotFoundException.class)
        public @ResponseBody ErrorInfo handleNotFound(HttpServletRequest req, Exception ex) {
            return new ErrorInfo(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        }

        record ErrorInfo(int code, String message) {}
    }
}
