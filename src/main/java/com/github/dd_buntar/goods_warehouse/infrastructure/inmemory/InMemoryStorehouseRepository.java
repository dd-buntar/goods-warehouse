package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryStorehouseRepository implements StorehouseRepository {
    private final Map<Long, Storehouse> storehouseStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Storehouse create(Storehouse entity) {
        Long nextId = idCounter.getAndIncrement();

        Storehouse newStorehouse = Storehouse.builder()
                .stockId(nextId)
                .shipmentId(entity.getShipmentId())
                .quantity(entity.getQuantity())
                .locationId(entity.getLocationId())
                .build();

        storehouseStorage.put(nextId, newStorehouse);
        return newStorehouse;
    }

    @Override
    public Optional<Storehouse> findById(Long id) {
        return Optional.ofNullable(storehouseStorage.get(id));
    }

    @Override
    public List<Storehouse> findAll() {
        return new ArrayList<>(storehouseStorage.values());
    }

    @Override
    public Optional<Storehouse> update(Storehouse entity) {
        if (entity.getStockId() == null) {
            return Optional.empty();
        }

        if (storehouseStorage.containsKey(entity.getStockId())) {
            storehouseStorage.put(entity.getStockId(), entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return storehouseStorage.remove(id) != null;
    }
}
