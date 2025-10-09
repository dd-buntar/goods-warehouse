package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryManufacturerRepository implements ManufacturerRepository {
    private  final Map<Long, Manufacturer> manufacturerStorage = new HashMap<>();

    @Override
    public Manufacturer create(Manufacturer entity) {
        return null;
    }

    @Override
    public Optional<Manufacturer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> findAll() {
        return null;
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
