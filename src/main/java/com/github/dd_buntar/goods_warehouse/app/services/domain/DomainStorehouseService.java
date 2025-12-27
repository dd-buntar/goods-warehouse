package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.app.services.ShipmentService;
import com.github.dd_buntar.goods_warehouse.app.services.StorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.StorehouseService;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainStorehouseService {
    private final StorehouseService storehouseService;
    private final ShipmentService shipmentService;
    private final StorageLocationService storageLocationService;

    public Storehouse create(final Storehouse entity) {
        validateStorehouseRelation(entity, shipmentService, storageLocationService);
        return storehouseService.create(entity);
    }

    public Storehouse findById(final Long id) {
        return storehouseService.findById(id);
    }

    public List<Storehouse> findAll() {
        return storehouseService.findAll();
    }

    public Storehouse update(final Storehouse entity) {
        validateStorehouseRelation(entity, shipmentService, storageLocationService);
        return storehouseService.update(entity);
    }

    public void deleteById(final Long id) {
        storehouseService.deleteById(id);
    }

    public List<Storehouse> findByLocationId(Long locationId) {
        return storehouseService.findByLocationId(locationId);
    }

    public List<Storehouse> findByShipmentId(Long shipmentId) {
        return storehouseService.findByShipmentId(shipmentId);
    }

    public List<Storehouse> findAll(int page, int size) {
        return storehouseService.findAll(page, size);
    }

    public long getTotalCount() {
        return storehouseService.getTotalCount();
    }

    public int getTotalPages(long totalCount, int size) {
        return storehouseService.getTotalPages(totalCount, size);
    }

    private void validateStorehouseRelation(Storehouse entity,
                                            ShipmentService shipmentService,
                                            StorageLocationService storageLocationService) {
        shipmentService.findById(entity.getShipmentId());
        storageLocationService.findById(entity.getLocationId());
    }
}
