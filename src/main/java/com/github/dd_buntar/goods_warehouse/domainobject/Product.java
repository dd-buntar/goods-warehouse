package com.github.dd_buntar.goods_warehouse.domainobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Integer productId;
    private String productName;
    private Integer manufacturerId;
    private Integer weight;
    private String description;
}
