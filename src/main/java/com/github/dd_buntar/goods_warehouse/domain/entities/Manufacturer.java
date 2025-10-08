package com.github.dd_buntar.goods_warehouse.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    private Long manufacturerId;
    private String manufacturerName;
    private String contactPhone;
    private String country;
}
