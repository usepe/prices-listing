package com.product.prices.infrastructure.outbound.database;

import com.product.prices.domain.model.BrandedProductPrice;
import com.product.prices.domain.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class H2PricesRepository implements PricesRepository {
    private final SpringDataH2PricesRepository pricesRepository;

    @Autowired
    public H2PricesRepository(SpringDataH2PricesRepository pricesRepository) {
        this.pricesRepository = pricesRepository;
    }

    @Override
    public List<BrandedProductPrice> listProductPrices(String brandId, String productId, LocalDateTime dateApplied) {
        return pricesRepository.findByPriceApplied(brandId, productId, dateApplied)
                .stream()
                .map(product -> new BrandedProductPrice(
                        product.brandId(),
                        product.productId(),
                        product.priceList(),
                        product.startDate(),
                        product.endDate(),
                        product.currency(),
                        product.price(),
                        product.priority()
                ))
                .toList();
    }
}
