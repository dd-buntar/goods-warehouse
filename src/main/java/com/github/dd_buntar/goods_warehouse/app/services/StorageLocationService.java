package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;
import lombok.NonNull;

import java.util.*;

public class StorageLocationService {
    private final StorageLocationRepository storageLocationRepository;

    public StorageLocationService(StorageLocationRepository storageLocationRepository) {
        this.storageLocationRepository = storageLocationRepository;
    }

    public Optional<StorageLocation> createStorageLocation(StorageLocation storageLocation) {
        validateRackNum(storageLocation.getRackNum());
        validateShelfNum(storageLocation.getShelfNum());
        Optional<StorageLocation> curLocation = storageLocationRepository.create(storageLocation);
        if (!curLocation.isPresent()) {
            throw new IllegalArgumentException("Такой стеллаж и полка уже существуют");
        }
        return curLocation;
    }

    public Optional<StorageLocation> findById(@NonNull final Long id) {
        Optional<StorageLocation> storageLocation = storageLocationRepository.findById(id);
        if (!storageLocation.isPresent()) {
            throw new IllegalArgumentException("Местоположения с таким id не существует");
        }
        return storageLocation;
    }

    public List<StorageLocation> findAll() {
        List<StorageLocation> locations = storageLocationRepository.findAll();
        if (locations.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return locations;
    }

    public Optional<StorageLocation> update(final StorageLocation storageLocation) {
        validateLocation(storageLocation);
        Optional<StorageLocation> curLocation = storageLocationRepository.update(storageLocation);
        if (!curLocation.isPresent()) {
            throw new IllegalArgumentException("Такого id нет в хранилище");
        }
        if (!curLocation.equals(storageLocation)) {
            throw new IllegalArgumentException("Такой стеллаж и полка уже существуют");
        }
        return curLocation;
    }

    public boolean deleteById(@NonNull final Long id) {
        boolean isDeleted = storageLocationRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Местоположения с таким id не существует");
        }
        return isDeleted;
    }

    public List<StorageLocation> findByRackNumber(@NonNull Integer rackNum) {
        List<StorageLocation> storageLocations = storageLocationRepository.findByRackNumber(rackNum);
        if (storageLocations.isEmpty()) {
            throw new IllegalArgumentException("Местоположений с таким номером стеллажа не существует");
        }
        return storageLocations;
    }

    public Set<Integer> findAllRackNumbers() {
        Set<Integer> racks = new HashSet<>(storageLocationRepository.findAllRackNumbers());
        if (racks.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return racks;
    }

    public List<Integer> findShelvesByRack(@NonNull Integer rackNum) {
        List<Integer> shelves = storageLocationRepository.findShelvesByRack(rackNum);
        if (shelves.isEmpty()) {
            throw new IllegalArgumentException("Стеллажа с таким номером не сущетсвует");
        }
        return shelves;
    }


    private void validateLocationId(Long locationId) {
        if (locationId == null || locationId <= 0) {
            throw new IllegalArgumentException("ID местоположения должен быть положительным числом");
        }
    }

    private void validateRackNum(Integer rackNum) {
        if (rackNum == null || rackNum <= 0) {
            throw new IllegalArgumentException("Номер стеллажа должен быть положительным числом");
        }
        if (rackNum > 100) {
            throw new IllegalArgumentException("Номер стеллажа не может превышать 100");
        }
    }

    private void validateShelfNum(Integer shelfNum) {
        if (shelfNum == null || shelfNum <= 0) {
            throw new IllegalArgumentException("Номер полки должен быть положительным числом");
        }
        if (shelfNum > 50) {
            throw new IllegalArgumentException("Номер полки не может превышать 50");
        }
    }

    private void validateShelfSequence(Integer rackNum, Integer shelfNum) {
        List<Integer> existingShelves = storageLocationRepository.findShelvesByRack(rackNum);

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

    private void validateLocation(StorageLocation storageLocation) {
        validateLocationId(storageLocation.getLocationId());
        validateRackNum(storageLocation.getRackNum());
        validateShelfNum(storageLocation.getShelfNum());
        validateShelfSequence(storageLocation.getRackNum(), storageLocation.getShelfNum());
    }
}
