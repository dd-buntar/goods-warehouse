package com.github.dd_buntar.goods_warehouse.infrastructure.services;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ShipmentRepository;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ShipmentService {
    private final ShipmentRepository shipmentRepository;

    public ShipmentService(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    public Optional<Shipment> create(final Shipment entity) {
        validateProductId(entity.getProductId());

        validatePrices(entity.getPurchasePrice(),
                entity.getSalePrice());

        validateDates(entity.getProductionDate(),
                entity.getExpiryDate(),
                entity.getArrivalDate());

        Optional<Shipment> curShipment = shipmentRepository.create(entity);
        if (!curShipment.isPresent()) {
            throw new IllegalArgumentException("Запись с таким id уже существует");
        }
        return curShipment;
    }

    public Optional<Shipment> findById(@NonNull final Long id) {
        return shipmentRepository.findById(id);
    }

    public List<Shipment> findAll() {
        return shipmentRepository.findAll();
    }

    public Optional<Shipment> update(final Shipment entity) {
        validateShipment(entity);
        Optional<Shipment> curShipment = shipmentRepository.update(entity);
        if (!curShipment.isPresent()) {
            throw new IllegalArgumentException("id поставки нет в хранилище");
        }
        return curShipment;
    }

    public boolean deleteById(@NonNull final Long id) {
        return shipmentRepository.deleteById(id);
    }

    public List<Shipment> findByProductId(@NonNull Long productId) {
        return shipmentRepository.findByProductId(productId);
    }

    public List<Shipment> findByProductionDate(@NonNull LocalDateTime productionDate) {
        return shipmentRepository.findByProductionDate(productionDate);
    }

    public List<Shipment> findByArrivalDate(@NonNull LocalDateTime arrivalDate) {
        return shipmentRepository.findByArrivalDate(arrivalDate);
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
