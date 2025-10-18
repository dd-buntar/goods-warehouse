package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> create(final Product entity) {
        validateProduct(entity);
        return productRepository.create(entity);
    }
    public Optional<Product> findById(@NonNull final Long id) {
        return productRepository.findById(id);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> update(final Product entity) {
        validateProduct(entity);
        Optional<Product> curProduct = productRepository.update(entity);
        if (!curProduct.isPresent()) {
            throw new IllegalArgumentException("id продукта нет в хранилище");
        }
        return curProduct;
    }
    public boolean deleteById(@NonNull final Long id) {
        return productRepository.deleteById(id);
    }

    public Optional<Product> findByName(String name) {
        validateProductName(name);
        return productRepository.findByName(name);
    }
    public List<Product> findByManufacturerId(@NonNull Long manufacturerId) {
        return productRepository.findByManufacturerId(manufacturerId);
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

    private void validateProduct(Product product) {
        validateProductId(product.getProductId());
        validateProductName(product.getProductName());
        validateManufacturerId(product.getManufacturerId());
        validateWeight(product.getWeight());
    }
}
