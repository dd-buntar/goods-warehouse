package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;

import java.util.List;
import java.util.Optional;

public interface StorehouseRepository extends Repository<Storehouse, Long> {
    List<Storehouse> findByLocationId(Long locationId);

    List<Storehouse> findByShipmentId(Long shipmentId);

}
