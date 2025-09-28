package com.github.dd_buntar.goods_warehouse.domainobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {
    private Integer shipmentId;
    private Integer productId;
    private Integer purchasePrice; // закупочная цена
    private Integer salePrice; // цена продажи
    private LocalDateTime productionDate; // дата изготовления
    private LocalDateTime expiryDate; // срок годности
    private LocalDateTime arrivalDate; // дата привоза на склад

}
