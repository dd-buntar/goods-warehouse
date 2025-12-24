package com.github.dd_buntar.goods_warehouse.domain.repositories.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ShipmentRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ShipmentRepositoryImpl implements ShipmentRepository {
    private final Map<Long, Shipment> shipmentStorage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    @Override
    public Optional<Shipment> create(Shipment entity) {
        if (shipmentStorage.containsKey(entity.getShipmentId())) {
            return Optional.empty();
        }
        Long nextId = idCounter.getAndIncrement();

        Shipment newShipment = Shipment.builder()
                .shipmentId(nextId)
                .productId(entity.getProductId())
                .purchasePrice(entity.getPurchasePrice())
                .salePrice(entity.getSalePrice())
                .productionDate(entity.getProductionDate())
                .expiryDate(entity.getExpiryDate())
                .arrivalDate(entity.getArrivalDate())
                .build();

        shipmentStorage.put(nextId, newShipment);
        return Optional.of(newShipment);
    }

    @Override
    public Optional<Shipment> findById(Long id) {
        return Optional.ofNullable(shipmentStorage.get(id));
    }

    @Override
    public List<Shipment> findAll() {
        return new ArrayList<>(shipmentStorage.values());
    }

    @Override
    public Optional<Shipment> update(Shipment entity) {
        if (shipmentStorage.containsKey(entity.getShipmentId())) {
            Long id = entity.getShipmentId();
            Shipment curShipment = shipmentStorage.get(id);

            if (entity.equals(curShipment)) {
                return Optional.of(curShipment);
            }

            Shipment shipmentToSave = Shipment.builder().shipmentId(entity.getShipmentId())
                    .productId(entity.getProductId())
                    .purchasePrice(entity.getPurchasePrice())
                    .salePrice(entity.getSalePrice())
                    .productionDate(entity.getProductionDate())
                    .expiryDate(entity.getExpiryDate())
                    .arrivalDate(entity.getArrivalDate())
                    .build();

            shipmentStorage.put(shipmentToSave.getShipmentId(), shipmentToSave);
            return Optional.of(shipmentToSave);
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        return shipmentStorage.remove(id) != null;
    }

    /**
     * Найти поставки по ID товара
     */
    @Override
    public List<Shipment> findByProductId(Long productId) {
        return shipmentStorage.values().stream()
                .filter(shipment -> shipment.getProductId().equals(productId))
                .collect(Collectors.toList());
    }

    /**
     * Найти поставки по дате производства
     */
    @Override
    public List<Shipment> findByProductionDate(LocalDateTime productionDate) {
        return shipmentStorage.values().stream()
                .filter(shipment -> shipment.getProductionDate() != null)
                .filter(shipment -> shipment.getProductionDate().toLocalDate().equals(productionDate.toLocalDate()))
                .collect(Collectors.toList());
    }

    /**
     * Найти поставки по дате прибытия
     */
    @Override
    public List<Shipment> findByArrivalDate(LocalDateTime arrivalDate) {
        return shipmentStorage.values().stream()
                .filter(shipment -> shipment.getArrivalDate() != null)
                .filter(shipment -> shipment.getArrivalDate().toLocalDate().equals(arrivalDate.toLocalDate()))
                .collect(Collectors.toList());
    }
}