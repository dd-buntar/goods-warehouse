package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStorageLocationRepository implements StorageLocationRepository {
    private final Map<Long, StorageLocation> storageLocationStorage = new HashMap<>();

    @Override
    public StorageLocation create(StorageLocation entity) {
        return null;
    }

    @Override
    public Optional<StorageLocation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<StorageLocation> findAll() {
        return null;
    }

    @Override
    public Optional<StorageLocation> update(StorageLocation entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
