package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.app.services.StorageLocationService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainStorageLocationService {
    private final StorageLocationService storageLocationService;
    private final DomainStorehouseService domainStorehouseService;

    public StorageLocation create(StorageLocation storageLocation) {
        return storageLocationService.createStorageLocation(storageLocation);
    }

    public StorageLocation findById(final Long id) {
        return storageLocationService.findById(id);
    }

    public List<StorageLocation> findAll() {
        return storageLocationService.findAll();
    }

    public StorageLocation update(final StorageLocation storageLocation) {
        return storageLocationService.update(storageLocation);
    }

    public void deleteById(Long locationId) {
        List<Storehouse> storehouseRecords = domainStorehouseService.findByLocationId(locationId);
        if (!storehouseRecords.isEmpty()) {
            for (Storehouse s : storehouseRecords) {
                domainStorehouseService.deleteById(s.getStockId());
            }
        }
        storageLocationService.deleteById(locationId);
    }

    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        return storageLocationService.findByRackNumber(rackNum);
    }

    public Set<Integer> findAllRackNumbers() {
        return storageLocationService.findAllRackNumbers();
    }

    public List<Integer> findShelvesByRack(Integer rackNum) {
        return storageLocationService.findShelvesByRack(rackNum);
    }
}
