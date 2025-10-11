package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProductRepositoryImpl implements ProductRepository {
    private final Map<Long, Product> productStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Product create(Product entity) {
        Long nextId = idCounter.getAndIncrement();

        Product newProduct = Product.builder()
                .productId(nextId)
                .productName(entity.getProductName())
                .manufacturerId(entity.getManufacturerId())
                .weight(entity.getWeight())
                .description(entity.getDescription())
                .build();

        productStorage.put(nextId, newProduct);
        return newProduct;
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
    public Optional<Product> update(Product entity) {
        if (entity.getProductId() == null) {
            return Optional.empty();
        }

        if (productStorage.containsKey(entity.getProductId())) {
            productStorage.put(entity.getProductId(), entity);
            return Optional.of(entity);
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

//    /**
//     * Найти товары по весу (диапазон)
//     */
//    @Override
//    public List<Product> findByWeightRange(Integer minWeight, Integer maxWeight) {
//        return productStorage.values().stream()
//                .filter(product -> product.getWeight() >= minWeight && product.getWeight() <= maxWeight)
//                .collect(Collectors.toList());
//    }

//    /**
//     * Получить все уникальные идентификаторы производителей
//     */
//    @Override
//    public Set<Long> findAllManufacturerIds() {
//        return productStorage.values().stream()
//                .map(Product::getManufacturerId)
//                .collect(Collectors.toSet());
//    }
}
