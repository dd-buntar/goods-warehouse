package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class StorehouseService {
    private final StorehouseRepository storehouseRepository;

    public Storehouse create(@NonNull final Storehouse entity) {
        validateShipmentId(entity.getShipmentId());
        validateLocationId(entity.getLocationId());
        validateQuantity(entity.getQuantity());
        Optional<Storehouse> curStorehouse = storehouseRepository.create(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("Запись с id= " + entity.getStockId() + " уже существует");
        }
        return curStorehouse.get();
    }

    public Storehouse findById(@NonNull final Long id) {
        Optional<Storehouse> storehouse = storehouseRepository.findById(id);
        if (!storehouse.isPresent()) {
            throw new IllegalArgumentException("Поставки на складе с id= " + id + " не существует");
        }
        return storehouse.get();
    }

    public List<Storehouse> findAll() {
        List<Storehouse> storehouses = storehouseRepository.findAll();
        if (storehouses.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return storehouses;
    }

    public Storehouse update(@NonNull final Storehouse entity) {
        validateStorehouse(entity);
        Optional<Storehouse> curStorehouse = storehouseRepository.update(entity);
        if (!curStorehouse.isPresent()) {
            throw new IllegalArgumentException("Поставки на складе с id= " + entity.getStockId() + " не существует");
        }
        return curStorehouse.get();
    }

    public void deleteById(@NonNull final Long id) {
        boolean isDeleted = storehouseRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Поставки на складе с id= " + id + " не существует");
        }
    }

    public List<Storehouse> findByLocationId(@NonNull final Long locationId) {
        List<Storehouse> storehouses = storehouseRepository.findByLocationId(locationId);
        if (storehouses.isEmpty()) {
            return new ArrayList<>();
            //throw new IllegalArgumentException("Поставки на складе с местоположением id= " + locationId + " не существует");
        }
        return storehouses;
    }

    public List<Storehouse> findByShipmentId(@NonNull final Long shipmentId) {
        List<Storehouse> storehouses = storehouseRepository.findByShipmentId(shipmentId);
        if (storehouses.isEmpty()) {
            return new ArrayList<>();
            //throw new IllegalArgumentException("Поставки с id= " + shipmentId + " на складе нет");
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
            throw new IllegalArgumentException("Количество товара не должно быть равно 0");
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
