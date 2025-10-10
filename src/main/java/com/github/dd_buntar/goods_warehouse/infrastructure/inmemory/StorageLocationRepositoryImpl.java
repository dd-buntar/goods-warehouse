package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class StorageLocationRepositoryImpl implements StorageLocationRepository {
    private final Map<Long, StorageLocation> storageLocationStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public StorageLocation create(StorageLocation entity) {
        Long nextId = idCounter.getAndIncrement();

        // Создаем копию объекта с новым ID
        StorageLocation newLocation = StorageLocation.builder()
                .locationId(nextId)
                .rackNum(entity.getRackNum())
                .shelfNum(entity.getShelfNum())
                .build();

        storageLocationStorage.put(nextId, newLocation);
        return newLocation;
    }

    @Override
    public Optional<StorageLocation> findById(Long id) {
        return Optional.ofNullable(storageLocationStorage.get(id));
    }

    @Override
    public List<StorageLocation> findAll() {
        return new ArrayList<>(storageLocationStorage.values());
    }

    @Override
    public Optional<StorageLocation> update(StorageLocation entity) {
        if (entity.getLocationId() == null) {
            return Optional.empty();
        }

        if (storageLocationStorage.containsKey(entity.getLocationId())) {
            storageLocationStorage.put(entity.getLocationId(), entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return storageLocationStorage.remove(id) != null;
    }


    /**
     * Найти все уникальные shipmentId в хранилище
     */
    public Optional<StorageLocation> findByRackAndShelf(Integer rackNum, Integer shelfNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum)
                        && location.getShelfNum().equals(shelfNum))
                .findFirst();
    }

    /**
     * Найти по номеру стеллажа
     */
    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum))
                .collect(Collectors.toList());
    }

    /**
     * Проверить, существует ли стеллаж и полка
     */
    public boolean existsByRackAndShelf(Integer rackNum, Integer shelfNum) {
        return storageLocationStorage.values().stream()
                .anyMatch(location -> location.getRackNum().equals(rackNum)
                        && location.getShelfNum().equals(shelfNum));
    }

    /**
     * Получить все уникальные номера стеллажей
     */
    public Set<Integer> findAllRackNumbers() {
        return storageLocationStorage.values().stream()
                .map(StorageLocation::getRackNum)
                .collect(Collectors.toSet());
    }

    /**
     * Получить все полки на конкретном стеллаже
     */
    public List<Integer> findShelvesByRack(Integer rackNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum))
                .map(StorageLocation::getShelfNum)
                .sorted()
                .collect(Collectors.toList());
    }

}
