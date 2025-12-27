package com.github.dd_buntar.goods_warehouse.domain.repositories.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;
import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {
    private final Map<Long, Product> productStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Optional<Product> create(Product entity) {
        if (productStorage.containsKey(entity.getProductId())) {
            return Optional.empty();
        }

        Long nextId = idCounter.getAndIncrement();

        Product newProduct = Product.builder()
                .productId(nextId)
                .productName(entity.getProductName())
                .manufacturerId(entity.getManufacturerId())
                .weight(entity.getWeight())
                .description(entity.getDescription())
                .build();

        productStorage.put(nextId, newProduct);
        return Optional.of(newProduct);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(productStorage.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(productStorage.values());
    }

    @Override
    public List<ProductWithQuantity> findAllWithQuantity() {
        return null;
    }

    @Override
    public Optional<Product> update(Product entity) {
        if (productStorage.containsKey(entity.getProductId())) {
            Long id = entity.getProductId();
            Product curProduct = productStorage.get(id);

            if (entity.equals(curProduct)) {
                return Optional.of(curProduct);
            }

            Product productToSave = Product.builder()
                    .productId(entity.getProductId())
                    .productName(entity.getProductName())
                    .manufacturerId(entity.getManufacturerId())
                    .weight(entity.getWeight())
                    .description(entity.getDescription())
                    .build();

            productStorage.put(productToSave.getProductId(), productToSave);
            return Optional.of(productToSave);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return productStorage.remove(id) != null;
    }

    /**
     * Найти товары по названию (точное совпадение)
     */
    @Override
    public Optional<Product> findByName(String name) {
        return productStorage.values().stream()
                .filter(product -> product.getProductName().equalsIgnoreCase(name))
                .findFirst();
    }

    /**
     * Найти товары по производителю
     */
    @Override
    public List<Product> findByManufacturerId(Long manufacturerId) {
        return productStorage.values().stream()
                .filter(product -> product.getManufacturerId().equals(manufacturerId))
                .collect(Collectors.toList());
    }
}
