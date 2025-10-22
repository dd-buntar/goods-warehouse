package com.github.dd_buntar.goods_warehouse.infrastructure.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ProductService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ShipmentService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorageLocationService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorehouseService;

import java.util.List;
import java.util.Optional;

public class DomainStorehouseService {
    private final StorehouseService storehouseService;
    private final ShipmentService shipmentService;
    private final StorageLocationService storageLocationService;

    public DomainStorehouseService(StorehouseService storehouseService,
                                   ShipmentService shipmentService,
                                   StorageLocationService storageLocationService) {
        this.storehouseService = storehouseService;
        this.shipmentService = shipmentService;
        this.storageLocationService = storageLocationService;
    }

    public Optional<Storehouse> create(final Storehouse entity) {
        validateStorehouseRelation(entity, shipmentService, storageLocationService);
        return storehouseService.create(entity);
    }

    public Optional<Storehouse> findById(final Long id) {
        return storehouseService.findById(id);
    }

    public List<Storehouse> findAll() {
        return storehouseService.findAll();
    }

    public Optional<Storehouse> update(final Storehouse entity) {
        validateStorehouseRelation(entity, shipmentService, storageLocationService);
        return storehouseService.update(entity);
    }

    public boolean deleteById(final Long id) {
        return storehouseService.deleteById(id);
    }

    public List<Storehouse> findByLocationId(Long locationId) {
        return storehouseService.findByLocationId(locationId);
    }

    public List<Storehouse> findByShipmentId(Long shipmentId) {
        return storehouseService.findByShipmentId(shipmentId);
    }

    private void validateStorehouseRelation(Storehouse storehouse,
                                            ShipmentService shipmentService,
                                            StorageLocationService storageLocationService) {
        if (!shipmentService.findById(storehouse.getShipmentId()).isPresent()) {
            throw new IllegalArgumentException("Поставки с таким id не существует");
        }
        if (!storageLocationService.findById(storehouse.getLocationId()).isPresent()) {
            throw new IllegalArgumentException("Локации с таким id не существует");
        }
    }
}
