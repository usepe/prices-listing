package com.product.prices.domain;

import com.product.prices.domain.exception.PriceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

@Service
public class DomainPricesService implements PricesService {
    private final PricesRepository repository;

    @Autowired
    public DomainPricesService(PricesRepository repository) {
        this.repository = repository;
    }

    @Override
    public BrandedProductPrice productPriceOnDate(String brandId, String productId, LocalDateTime dateApplied) {
        return repository.listProductPrices(brandId, productId)
                .stream()
                .filter(price -> Objects.nonNull(dateApplied) && price.startDate().isBefore(dateApplied) && price.endDate().isAfter(dateApplied))
                .max(Comparator.comparing(BrandedProductPrice::priority))
                .orElseThrow(PriceNotFoundException::new);
    }
}
