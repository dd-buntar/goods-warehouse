package com.github.dd_buntar.goods_warehouse.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocation {
    private Long locationId;
    private Integer rackNum; // стеллаж
    private Integer shelfNum; // полка
}
