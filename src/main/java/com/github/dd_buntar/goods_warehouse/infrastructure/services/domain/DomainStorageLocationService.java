package com.github.dd_buntar.goods_warehouse.infrastructure.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorageLocationService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DomainStorageLocationService {
    private final StorageLocationService storageLocationService;
    private final DomainStorehouseService domainStorehouseService;

    public DomainStorageLocationService(StorageLocationService storageLocationService,
                                        DomainStorehouseService domainStorehouseService) {
        this.storageLocationService = storageLocationService;
        this.domainStorehouseService = domainStorehouseService;
    }

    public Optional<StorageLocation> create(StorageLocation storageLocation) {
        return storageLocationService.createStorageLocation(storageLocation);
    }

    public Optional<StorageLocation> findById(final Long id) {
        return storageLocationService.findById(id);
    }

    public List<StorageLocation> findAll() {
        return storageLocationService.findAll();
    }

    public Optional<StorageLocation> update(final StorageLocation storageLocation) {
        return storageLocationService.update(storageLocation);
    }

    public boolean deleteById(Long locationId) {
        List<Storehouse> storehouseRecords = domainStorehouseService.findByLocationId(locationId);
        if (!storehouseRecords.isEmpty()) {
            for (Storehouse s : storehouseRecords) {
                domainStorehouseService.deleteById(s.getStockId());
            }
        }
        return storageLocationService.deleteById(locationId);
    }

    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        List<StorageLocation> locations = storageLocationService.findByRackNumber(rackNum);
        if (locations.isEmpty()) {
            throw new IllegalArgumentException("Стеллажа с таким номером не существует");
        }
        return locations;
    }

    public Set<Integer> findAllRackNumbers() {
        return storageLocationService.findAllRackNumbers();
    }

    public List<Integer> findShelvesByRack(Integer rackNum) {
        List<Integer> shelves = storageLocationService.findShelvesByRack(rackNum);
        if (shelves.isEmpty()) {
            throw new IllegalArgumentException("Стеллажа с таким номером не существует");
        }
        return shelves;
    }
}
