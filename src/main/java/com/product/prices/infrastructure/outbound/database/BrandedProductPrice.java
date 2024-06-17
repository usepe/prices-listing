package com.product.prices.infrastructure.outbound.database;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "prices")
public class BrandedProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "brand_id")
    private String brandId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "price_list")
    private String priceList;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "priority")
    private int priority;

    public BrandedProductPrice() {
    }

    public BrandedProductPrice(long id,
                               String brandId,
                               String productId,
                               String priceList,
                               LocalDateTime startDate,
                               LocalDateTime endDate,
                               String currency,
                               BigDecimal price,
                               int priority) {
        this.id = id;
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
        return id == that.id && priority == that.priority && Objects.equals(brandId, that.brandId) && Objects.equals(productId, that.productId) && Objects.equals(priceList, that.priceList) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(currency, that.currency) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brandId, productId, priceList, startDate, endDate, currency, price, priority);
    }

    @Override
    public String toString() {
        return "BrandedProductPrice{" +
                "id=" + id +
                ", brandId='" + brandId + '\'' +
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
