package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;

import java.time.LocalDateTime;
import java.util.List;

public interface ShipmentRepository extends Repository<Shipment, Long> {
    List<Shipment> findByProductId(Long productId);
    List<Shipment> findByProductionDate(LocalDateTime productionDate);
    List<Shipment> findByArrivalDate(LocalDateTime arrivalDate);
}
