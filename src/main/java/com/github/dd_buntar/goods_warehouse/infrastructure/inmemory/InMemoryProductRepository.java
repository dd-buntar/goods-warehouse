package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<Long, Product> productStorage = new HashMap<>();

    @Override
    public Product create(Product entity) {
        return null;
    }

    @Override
    public Optional<Product> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public Optional<Product> update(Product entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
