package com.github.dd_buntar.goods_warehouse.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private Long manufacturerId;
    private Integer weight;
    private String description;
}
