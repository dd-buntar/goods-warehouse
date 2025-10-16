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
    public Optional<StorageLocation> create(StorageLocation entity) {
        Long nextId = idCounter.getAndIncrement();

        if (findByRackAndShelf(entity.getRackNum(), entity.getShelfNum()).isPresent()) {
            return Optional.empty();
        }
        StorageLocation newLocation = StorageLocation.builder()
                .locationId(nextId)
                .rackNum(entity.getRackNum())
                .shelfNum(entity.getShelfNum())
                .build();

        storageLocationStorage.put(nextId, newLocation);
        return Optional.of(newLocation);
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
        if (storageLocationStorage.containsKey(entity.getLocationId())) {
            Long id = entity.getLocationId();
            StorageLocation curEntity = storageLocationStorage.get(id);
            if (entity.getRackNum().equals(curEntity.getRackNum()) &&
                    entity.getShelfNum().equals(curEntity.getShelfNum())) {
                return Optional.of(curEntity);
            }

            if (findByRackAndShelf(entity.getRackNum(), entity.getShelfNum()).isPresent()) {
                return Optional.empty();
            }

            StorageLocation locationToSave = StorageLocation.builder()
                    .locationId(entity.getLocationId())
                    .rackNum(entity.getRackNum())
                    .shelfNum(entity.getShelfNum())
                    .build();

            storageLocationStorage.put(locationToSave.getLocationId(), locationToSave);
            return Optional.of(locationToSave);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return storageLocationStorage.remove(id) != null;
    }

    /**
     * Найти по номеру стеллажа
     */
    @Override
    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum))
                .collect(Collectors.toList());
    }

    /**
     * Получить все уникальные номера стеллажей
     */
    @Override
    public Set<Integer> findAllRackNumbers() {
        return storageLocationStorage.values().stream()
                .map(StorageLocation::getRackNum)
                .collect(Collectors.toSet());
    }

    /**
     * Получить все полки на конкретном стеллаже
     */
    @Override
    public List<Integer> findShelvesByRack(Integer rackNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum))
                .map(StorageLocation::getShelfNum)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Найти, существует ли локация с таким номером стеллажа и номером полки
     */
    private Optional<StorageLocation> findByRackAndShelf(Integer rackNum, Integer shelfNum) {
        return storageLocationStorage.values().stream()
                .filter(location -> location.getRackNum().equals(rackNum)
                        && location.getShelfNum().equals(shelfNum))
                .findFirst();
    }
}
