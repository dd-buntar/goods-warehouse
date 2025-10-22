package com.github.dd_buntar.goods_warehouse.infrastructure.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ProductService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorageLocationService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorehouseService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class DomainStorageLocationService {
    private final StorageLocationService storageLocationService;
    private final StorehouseService storehouseService;

    public DomainStorageLocationService(StorageLocationService storageLocationService,
                                        StorehouseService storehouseService) {
        this.storageLocationService = storageLocationService;
        this.storehouseService = storehouseService;
    }

    public Optional<StorageLocation> create(StorageLocation storageLocation) {
        validateLocation(storageLocation);
        return storageLocationService.createStorageLocation(storageLocation);
    }

    public Optional<StorageLocation> findById(final Long id) {
        return storageLocationService.findById(id);
    }

    public List<StorageLocation> findAll() {
        return storageLocationService.findAll();
    }

    public Optional<StorageLocation> update(final StorageLocation storageLocation) {
        validateLocation(storageLocation);
        return storageLocationService.update(storageLocation);
    }

    public boolean deleteById(Long locationId) {
        // Проверяем, используется ли местоположение в Storehouse
        List<Storehouse> storehouseRecords = storehouseService.findByLocationId(locationId);

        if (!storehouseRecords.isEmpty()) {
            for (Storehouse s : storehouseRecords) {
                storehouseService.deleteById(s.getStockId());
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


    private void validateLocation(StorageLocation storageLocation) {
        // Максимальный номер стеллажа - 100
        if (storageLocation.getRackNum() > 100) {
            throw new IllegalArgumentException("Номер стеллажа не может превышать 100");
        }

        // Максимальный номер полки - 50
        if (storageLocation.getShelfNum() > 50) {
            throw new IllegalArgumentException("Номер полки не может превышать 50");
        }

        // Проверяем логическую последовательность полок
        validateShelfSequence(storageLocation.getRackNum(), storageLocation.getShelfNum());
    }

    private void validateShelfSequence(Integer rackNum, Integer shelfNum) {
        List<Integer> existingShelves = storageLocationService.findShelvesByRack(rackNum);

        // новая полка не должна быть больше существующих более чем на 1
        if (!existingShelves.isEmpty()) {
            Integer maxExistingShelf = Collections.max(existingShelves);

            if (shelfNum > maxExistingShelf + 1) {
                throw new IllegalArgumentException(
                        "Новая полка (" + shelfNum + ") не может быть больше максимальной существующей (" +
                                maxExistingShelf + ") более чем на 1");
            }
        }
    }
}
