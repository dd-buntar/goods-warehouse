package com.github.dd_buntar.goods_warehouse.domainobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    private Integer manufacturerId;
    private String manufacturerName;
    private String contactPhone;
    private String country;
}
