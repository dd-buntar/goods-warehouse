package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStorageLocationRepository implements StorageLocationRepository {
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
}
