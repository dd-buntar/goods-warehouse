package com.github.dd_buntar.goods_warehouse.app.services;

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
        validateShipmentId(entity.getShipmentId());
        validateLocationId(entity.getLocationId());
        validateQuantity(entity.getQuantity());
        Optional<Storehouse> curStorehouse = storehouseRepository.create(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("Запись с таким id уже существует");
        }
        return curStorehouse;
    }

    public Optional<Storehouse> findById(@NonNull final Long id) {
        Optional<Storehouse> storehouse = storehouseRepository.findById(id);
        if (!storehouse.isPresent()) {
            throw new IllegalArgumentException("Поставки на складе с таким id не существует");
        }
        return storehouse;
    }

    public List<Storehouse> findAll() {
        List<Storehouse> storehouses = storehouseRepository.findAll();
        if (storehouses.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return storehouses;
    }

    public Optional<Storehouse> update(final Storehouse entity) {
        validateStorehouse(entity);
        Optional<Storehouse> curStorehouse = storehouseRepository.update(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("Поставки на складе с таким id не существует");
        }
        return curStorehouse;
    }

    public boolean deleteById(@NonNull final Long id) {
        boolean isDeleted = storehouseRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Поставки на складе с таким id не существует");
        }
        return isDeleted;
    }

    public List<Storehouse> findByLocationId(@NonNull Long locationId) {
        List<Storehouse> storehouses = storehouseRepository.findByLocationId(locationId);
        if (storehouses.isEmpty()) {
            throw new IllegalArgumentException("Поставки на складе с таким местоположением не существует");
        }
        return storehouses;
    }

    public List<Storehouse> findByShipmentId(@NonNull Long shipmentId) {
        List<Storehouse> storehouses = storehouseRepository.findByShipmentId(shipmentId);
        if (storehouses.isEmpty()) {
            throw new IllegalArgumentException("Такой поставки на складе нет");
        }
        return storehouses;
    }

    private void validateStockId(Long stockId) {
        if (stockId == null || stockId <= 0) {
            throw new IllegalArgumentException("Id поставки на складе должен быть положительным числом");
        }
    }

    private void validateShipmentId(Long shipmentId) {
        if (shipmentId == null || shipmentId <= 0) {
            throw new IllegalArgumentException("Id поставки на складе должен быть положительным числом");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null || quantity == 0) {
            throw new IllegalArgumentException("Количество товара не должно быть отрицательным");
        }

    }

    private void validateLocationId(Long locationId) {
        if (locationId == null || locationId <= 0) {
            throw new IllegalArgumentException("Id местоположения должен быть положительным числом");
        }
    }

    private void validateStorehouse(Storehouse storehouse) {
        validateStockId(storehouse.getStockId());
        validateShipmentId(storehouse.getShipmentId());
        validateLocationId(storehouse.getLocationId());
        validateQuantity(storehouse.getQuantity());
    }
}
