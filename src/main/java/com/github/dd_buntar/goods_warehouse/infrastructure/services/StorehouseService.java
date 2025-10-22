package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public class StorehouseService {
    private final StorehouseRepository storehouseRepository;

    public StorehouseService(StorehouseRepository storehouseRepository) {
        this.storehouseRepository = storehouseRepository;
    }

    public Optional<Storehouse> create(final Storehouse entity) {
        validateStorehouse(entity);
        Optional<Storehouse> curStorehouse = storehouseRepository.create(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("Запись с таким id уже существует");
        }
        return curStorehouse;
    }

    public Optional<Storehouse> findById(@NonNull final Long id) {
        return storehouseRepository.findById(id);
    }

    public List<Storehouse> findAll() {
        return storehouseRepository.findAll();
    }

    public Optional<Storehouse> update(final Storehouse entity) {
        validateStorehouse(entity);
        Optional<Storehouse> curStorehouse = storehouseRepository.update(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("id склада нет в хранилище");
        }
        return curStorehouse;
    }

    public boolean deleteById(@NonNull final Long id) {
        return storehouseRepository.deleteById(id);
    }

    public List<Storehouse> findByLocationId(@NonNull Long locationId) {
        return storehouseRepository.findByLocationId(locationId);
    }

    public List<Storehouse> findByShipmentId(@NonNull Long shipmentId) {
        return storehouseRepository.findByShipmentId(shipmentId);
    }

    private void validateStockId(Long stockId) {
        if (stockId == null || stockId <= 0) {
            throw new IllegalArgumentException("ID склада должен быть положительным числом");
        }
    }

    private void validateShipmentId(Long shipmentId) {
        if (shipmentId == null || shipmentId <= 0) {
            throw new IllegalArgumentException("ID поставки должен быть положительным числом");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity == 0) {
            throw new IllegalArgumentException("Количество товара не должно быть отрицательным");
        }

    }

    private void validateLocationId(Long locationId) {
        if (locationId == null || locationId <= 0) {
            throw new IllegalArgumentException("ID местоположения должен быть положительным числом");
        }
    }

    private void validateStorehouse(Storehouse storehouse) {
        validateStockId(storehouse.getStockId());
        validateShipmentId(storehouse.getShipmentId());
        validateLocationId(storehouse.getLocationId());
        validateQuantity(storehouse.getQuantity());
    }
}
