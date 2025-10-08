package com.github.dd_buntar.goods_warehouse.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storehouse {
    private Long stockId;
    private Long shipmentId;
    private Integer quantity;
    private Long locationId;


}
