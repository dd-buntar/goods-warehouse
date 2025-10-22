package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        return storageLocationRepository.findById(id);
    }

    public List<StorageLocation> findAll() {
        return storageLocationRepository.findAll();
    }

    public Optional<StorageLocation> update(final StorageLocation storageLocation) {
        validateLocation(storageLocation);

        Optional<StorageLocation> curLocation = storageLocationRepository.update(storageLocation);
        if (!curLocation.isPresent()) {
            throw new IllegalArgumentException("Такой стеллаж и полка уже существуют или id нет в хранилище");
        }

        return curLocation;
    }

    public boolean deleteById(@NonNull final Long id) {
        return storageLocationRepository.deleteById(id);
    }

    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        validateRackNum(rackNum);
        return storageLocationRepository.findByRackNumber(rackNum);
    }

    public Set<Integer> findAllRackNumbers() {
        return storageLocationRepository.findAllRackNumbers();
    }

    public List<Integer> findShelvesByRack(Integer rackNum) {
        validateRackNum(rackNum);
        return storageLocationRepository.findShelvesByRack(rackNum);
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
