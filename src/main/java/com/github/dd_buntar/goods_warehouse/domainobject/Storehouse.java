package com.github.dd_buntar.goods_warehouse.domainobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Storehouse {
    private Integer stockId;
    private Integer shipmentId;
    private Integer quantity;
    private Integer locationId;


}
