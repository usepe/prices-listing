package com.product.prices.application;

import com.product.prices.domain.exception.PriceNotFoundException;
import com.product.prices.domain.model.BrandedProductPrice;
import com.product.prices.domain.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
public class PricesService implements com.product.prices.domain.service.PricesService {
    private final PricesRepository repository;

    @Autowired
    public PricesService(PricesRepository repository) {
        this.repository = repository;
    }

    @Override
    public BrandedProductPrice productPriceOnDate(String brandId, String productId, LocalDateTime dateApplied) {
        return repository.listProductPrices(brandId, productId, dateApplied)
                .stream()
                .max(Comparator.comparing(BrandedProductPrice::priority))
                .orElseThrow(PriceNotFoundException::new);
    }
}
