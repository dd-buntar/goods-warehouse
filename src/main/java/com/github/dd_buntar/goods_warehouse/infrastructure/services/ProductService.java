package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;

import java.util.List;
import java.util.Optional;

public class ProductService {
    public Optional<Product> create(final Product entity) {


        return null;
    }
    public Optional<Product> findById(final Long id) {
        return null;
    }
    public List<Product> findAll() {
        return null;
    }
    public Optional<Product> update(final Product entity) {
        return null;
    }
    public boolean deleteById(final Long id) {
        return false;
    }

    public Optional<Product> findByName(String name) {
        return null;
    }
    public List<Product> findByManufacturerId(Long manufacturerId) {
        return null;
    }

    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("ID продукта должен быть положительным числом");
        }
    }

    private void validateProductName(String productName) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Название продукта не должно быть пустой строкой");
        }
    }

    private void validateManufacturerId(Long manufacturerId) {
        if (manufacturerId == null || manufacturerId <= 0) {
            throw new IllegalArgumentException("ID производителя должен быть положительным числом");
        }
    }

    private void validateWeight(Integer weight) {
        if (weight == null || weight <= 0) {
            throw new IllegalArgumentException("Вес продукта должен быть положительным числом");
        }
    }
}
