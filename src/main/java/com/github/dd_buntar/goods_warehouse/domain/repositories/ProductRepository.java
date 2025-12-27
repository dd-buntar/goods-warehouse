package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long>{
    Optional<Product> findByName(String name);

    List<Product> findByManufacturerId(Long manufacturerId);
    List<ProductWithQuantity> findAllWithQuantity();
}
