package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository extends Repository<Product, Long>{
    Optional<Product> findByName(String name);
    List<Product> findByManufacturerId(Long manufacturerId);
}
