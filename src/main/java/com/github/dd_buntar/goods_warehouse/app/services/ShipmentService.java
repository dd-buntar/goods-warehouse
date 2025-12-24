package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ShipmentRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;

    public Shipment create(@NonNull final Shipment entity) {
        validateProductId(entity.getProductId());

        validatePrices(entity.getPurchasePrice(),
                entity.getSalePrice());

        validateDates(
                entity.getProductionDate(),
                entity.getExpiryDate(),
                entity.getArrivalDate()
        );

        Optional<Shipment> curShipment = shipmentRepository.create(entity);
        if (!curShipment.isPresent()) {
            throw new IllegalArgumentException("Запись с id= " + entity.getShipmentId() + " уже существует");
        }
        return curShipment.get();
    }

    public Shipment findById(@NonNull final Long id) {
        Optional<Shipment> shipment = shipmentRepository.findById(id);
        if (!shipment.isPresent()) {
            throw new IllegalArgumentException("Запись с id= " + id + " не существует");
        }
        return shipment.get();
    }

    public List<Shipment> findAll() {
        List<Shipment> shipments = shipmentRepository.findAll();
        if (shipments.isEmpty()) {
            throw new IllegalArgumentException("Таблица пустая");
        }
        return shipments;
    }

    public Shipment update(@NonNull final Shipment entity) {
        validateShipment(entity);
        Optional<Shipment> curShipment = shipmentRepository.update(entity);
        if (!curShipment.isPresent()) {
            throw new IllegalArgumentException("Запись с id= " + entity.getShipmentId() + " не существует");
        }
        return curShipment.get();
    }

    public void deleteById(@NonNull final Long id) {
        boolean isDeleted = shipmentRepository.deleteById(id);
        if (!isDeleted) {
            throw new IllegalArgumentException("Запись с id= " + id + " не существует");
        }
    }

    public List<Shipment> findByProductId(@NonNull final Long productId) {
        List<Shipment> shipments = shipmentRepository.findByProductId(productId);
        if (shipments.isEmpty()) {
            return new ArrayList<>();
            //throw new IllegalArgumentException("Поставок с продуктом id=" + productId + " не существует");
        }
        return shipments;
    }

    public List<Shipment> findByProductionDate(@NonNull final LocalDateTime productionDate) {
        List<Shipment> shipments = shipmentRepository.findByProductionDate(productionDate);
        if (shipments.isEmpty()) {
            throw new IllegalArgumentException("Поставок с датой изготовления " + productionDate + " не существует");
        }
        return shipments;
    }

    public List<Shipment> findByArrivalDate(@NonNull final LocalDateTime arrivalDate) {
        List<Shipment> shipments = shipmentRepository.findByArrivalDate(arrivalDate);
        if (shipments.isEmpty()) {
            throw new IllegalArgumentException("Поставок с датой привоза " + arrivalDate + " не существует");
        }
        return shipments;
    }

    private void validateShipmentId(Long shipmentId) {
        if (shipmentId == null || shipmentId <= 0) {
            throw new IllegalArgumentException("ID поставки должен быть положительным числом");
        }
    }

    private void validateProductId(Long productId) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("ID продукта должен быть положительным числом");
        }
    }

    private void validatePrices(Integer purchasePrice, Integer salePrice) {
        if (purchasePrice == null || purchasePrice <= 0) {
            throw new IllegalArgumentException("Закупочная цена должна быть больше нуля");
        }
        if (salePrice == null || salePrice <= 0) {
            throw new IllegalArgumentException("Цена продажи должна быть больше нуля");
        }
        if (salePrice < purchasePrice) {
            throw new IllegalArgumentException("Убедитесь, что цена продажи >= закупочная цена");
        }
    }

    private void validateDates(LocalDateTime productionDate, LocalDateTime expiryDate, LocalDateTime arrivalDate) {
        if (productionDate == null || expiryDate == null || arrivalDate == null) {
            throw new IllegalArgumentException("Не все даты существуют");
        }
        if (productionDate.isAfter(expiryDate)) {
            throw new IllegalArgumentException("Дата изготовления больше срока годности");
        }
        if (productionDate.isAfter(arrivalDate)) {
            throw new IllegalArgumentException("Дата изготовления больше даты привоза на склад");
        }
    }

    private void validateShipment(Shipment shipment) {
        validateShipmentId(shipment.getShipmentId());
        validateProductId(shipment.getProductId());

        validatePrices(shipment.getPurchasePrice(),
                shipment.getSalePrice());

        validateDates(shipment.getProductionDate(),
                shipment.getExpiryDate(),
                shipment.getArrivalDate());
    }
}
