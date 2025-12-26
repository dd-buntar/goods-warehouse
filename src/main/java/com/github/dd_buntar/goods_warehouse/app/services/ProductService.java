package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;
import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product create(@NonNull final Product entity) {
        validateProductName(entity.getProductName());
        validateManufacturerId(entity.getManufacturerId());
        validateWeight(entity.getWeight());
        Optional<Product> curProduct = productRepository.create(entity);
        if (!curProduct.isPresent()) {
            throw new IllegalArgumentException("Запись с id= " + entity.getProductId() + " уже существует");
        }
        return curProduct.get();
    }

    public Product findById(@NonNull final Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new IllegalArgumentException("Продукта с id= " + id + " не существует");
        }
        return product.get();
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return products;
    }

    public List<ProductWithQuantity> findAllWithQuantity() {
        List<ProductWithQuantity> products = productRepository.findAllWithQuantity();
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return products;
    }

    public Product update(@NonNull final Product entity) {
        validateProductName(entity.getProductName());
        validateManufacturerId(entity.getManufacturerId());
        validateWeight(entity.getWeight());
        Optional<Product> curProduct = productRepository.update(entity);
        if (!curProduct.isPresent()) {
            throw new IllegalArgumentException("Продукта с id= " + entity.getProductId() + " не существует");
        }
        return curProduct.get();
    }

    public void deleteById(@NonNull final Long id) {
        boolean isDeleted = productRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Продукта с id= " + id + " не существует");
        }
    }

    public Product findByName(@NonNull final String name) {
        Optional<Product> product = productRepository.findByName(name);
        if (!product.isPresent()) {
            throw new IllegalArgumentException("Продукта с именем " + name + " не существует");
        }
        return product.get();
    }

    public List<Product> findByManufacturerId(@NonNull final Long manufacturerId) {
        List<Product> products = productRepository.findByManufacturerId(manufacturerId);
        if (products.isEmpty()) {
            return new ArrayList<>();
            //throw new IllegalArgumentException("Продуктов с производителем " + manufacturerId + " не существует");
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
}
