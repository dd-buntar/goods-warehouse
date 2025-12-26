package com.github.dd_buntar.goods_warehouse.domain.repositories.dto;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductWithQuantity {
    public Product product;
    public Integer quantity;
    public String manufacturerName;
}
