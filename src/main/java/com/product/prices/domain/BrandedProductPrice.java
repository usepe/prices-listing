package com.product.prices.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class BrandedProductPrice {
    private final String brandId;
    private final String productId;
    private final String priceList;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final String currency;
    private final BigDecimal price;
    private final int priority;

    public BrandedProductPrice(String brandId,
                               String productId,
                               String priceList,
                               LocalDateTime startDate,
                               LocalDateTime endDate,
                               String currency,
                               BigDecimal price,
                               int priority) {
        this.brandId = brandId;
        this.productId = productId;
        this.priceList = priceList;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.price = price;
        this.priority = priority;
    }

    public String brandId() {
        return brandId;
    }

    public String productId() {
        return productId;
    }

    public String priceList() {
        return priceList;
    }

    public LocalDateTime startDate() {
        return startDate;
    }

    public LocalDateTime endDate() {
        return endDate;
    }

    public String currency() {
        return currency;
    }

    public BigDecimal price() {
        return price;
    }

    public int priority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandedProductPrice that = (BrandedProductPrice) o;
        return priority == that.priority && Objects.equals(brandId, that.brandId) && Objects.equals(productId, that.productId) && Objects.equals(priceList, that.priceList) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(currency, that.currency) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandId, productId, priceList, startDate, endDate, currency, price, priority);
    }

    @Override
    public String toString() {
        return "BrandedProductPrice{" +
                "brandId='" + brandId + '\'' +
                ", productId='" + productId + '\'' +
                ", priceList='" + priceList + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", priority=" + priority +
                '}';
    }
}
