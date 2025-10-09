package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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


    /**
     * Найти все записи по ID местоположения
     */
    public List<Storehouse> findByLocationId(Long locationId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getLocationId().equals(locationId))
                .collect(Collectors.toList());
    }

    /**
     * Найти все записи по ID поставки
     */
    public List<Storehouse> findByShipmentId(Long shipmentId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getShipmentId().equals(shipmentId))
                .collect(Collectors.toList());
    }

    /**
     * Найти запись по поставке и местоположению
     */
    public Optional<Storehouse> findByShipmentAndLocation(Long shipmentId, Long locationId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getShipmentId().equals(shipmentId)
                        && storehouse.getLocationId().equals(locationId))
                .findFirst();
    }

    /**
     * Получить количество конкретного товара (по shipmentId) на складе
     */
    public Integer getQuantityByShipment(Long shipmentId) {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getShipmentId().equals(shipmentId))
                .mapToInt(Storehouse::getQuantity)
                .sum();
    }

    /**
     * Найти местоположения с положительным количеством товара (есть в наличии)
     */
    public List<Storehouse> findAvailableStock() {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    /**
     * Найти местоположения с отрицательным количеством (недостача/резерв)
     */
    public List<Storehouse> findNegativeStock() {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getQuantity() < 0)
                .collect(Collectors.toList());
    }

    /**
     * Найти местоположения с нулевым количеством
     */
    public List<Storehouse> findZeroStock() {
        return storehouseStorage.values().stream()
                .filter(storehouse -> storehouse.getQuantity() == 0)
                .collect(Collectors.toList());
    }

    /**
     * Найти все уникальные shipmentId в хранилище
     */
    public Set<Long> findAllShipmentIds() {
        return storehouseStorage.values().stream()
                .map(Storehouse::getShipmentId)
                .collect(Collectors.toSet());
    }

    /**
     * Найти все уникальные locationId в хранилище
     */
    public Set<Long> findAllLocationIds() {
        return storehouseStorage.values().stream()
                .map(Storehouse::getLocationId)
                .collect(Collectors.toSet());
    }
}
