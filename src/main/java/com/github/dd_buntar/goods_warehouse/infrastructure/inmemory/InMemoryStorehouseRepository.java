package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStorehouseRepository implements StorehouseRepository {
    private final Map<Long, Storehouse> storehouseStorage = new HashMap<>();

    @Override
    public Storehouse create(Storehouse entity) {
        return null;
    }

    @Override
    public Optional<Storehouse> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Storehouse> findAll() {
        return null;
    }

    @Override
    public Optional<Storehouse> update(Storehouse entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
