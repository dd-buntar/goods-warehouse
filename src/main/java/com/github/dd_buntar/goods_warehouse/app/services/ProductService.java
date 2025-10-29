package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
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
        Optional<Product> curProduct = productRepository.create(entity);
        if (!curProduct.isPresent()) {
            throw new IllegalArgumentException("Запись с таким id уже существует");
        }
        return curProduct;
    }

    public Optional<Product> findById(@NonNull final Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new IllegalArgumentException("Продукта с таким id не существует");
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return products;
    }

    public Optional<Product> update(final Product entity) {
        validateProductName(entity.getProductName());
        validateManufacturerId(entity.getManufacturerId());
        validateWeight(entity.getWeight());
        Optional<Product> curProduct = productRepository.update(entity);
        if (!curProduct.isPresent()) {
            throw new IllegalArgumentException("id продукта нет в хранилище");
        }
        return curProduct;
    }

    public boolean deleteById(@NonNull final Long id) {
        boolean isDeleted = productRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Продукта с таким id не существует");
        }
        return isDeleted;
    }

    public Optional<Product> findByName(String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (!product.isPresent()) {
            throw new IllegalArgumentException("Продукта с таким именем не существует");
        }
        return product;
    }

    public List<Product> findByManufacturerId(@NonNull Long manufacturerId) {
        List<Product> products = productRepository.findByManufacturerId(manufacturerId);
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Продуктов с таким производителем не существует");
        }
        return products;
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
