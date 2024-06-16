package com.product.prices.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException() {
        super("Price not found for given product");
    }
}
