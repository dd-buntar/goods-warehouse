package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class StorehouseRepositoryImpl implements StorehouseRepository {
    private final Map<Long, Storehouse> storehouseStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Optional<Storehouse> create(Storehouse entity) {
        if (storehouseStorage.containsKey(entity.getStockId())) {
            return Optional.empty();
        }

        Long nextId = idCounter.getAndIncrement();

        Storehouse newStorehouse = Storehouse.builder()
                .stockId(nextId)
                .shipmentId(entity.getShipmentId())
                .quantity(entity.getQuantity())
                .locationId(entity.getLocationId())
                .build();

        storehouseStorage.put(nextId, newStorehouse);
        return Optional.of(newStorehouse);
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
        if (storehouseStorage.containsKey(entity.getStockId())) {
            Long id = entity.getStockId();
            Storehouse curStorehouse = storehouseStorage.get(id);

            if (entity.equals(curStorehouse)) {
                return Optional.of(curStorehouse);
            }

            Storehouse storehouseToSave = Storehouse.builder()
                    .stockId(entity.getStockId())
                    .shipmentId(entity.getShipmentId())
                    .locationId(entity.getLocationId())
                    .quantity(entity.getQuantity())
                    .build();

            storehouseStorage.put(storehouseToSave.getStockId(), storehouseToSave);
            return Optional.of(storehouseToSave);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return storehouseStorage.remove(id) != null;
    }


    /**
     * Найти все записи по ID местоположения
     */
    @Override
    public List<Storehouse> findByLocationId(Long locationId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getLocationId().equals(locationId))
                .collect(Collectors.toList());
    }

    /**
     * Найти все записи по ID поставки
     */
    @Override
    public List<Storehouse> findByShipmentId(Long shipmentId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getShipmentId().equals(shipmentId))
                .collect(Collectors.toList());
    }
}
