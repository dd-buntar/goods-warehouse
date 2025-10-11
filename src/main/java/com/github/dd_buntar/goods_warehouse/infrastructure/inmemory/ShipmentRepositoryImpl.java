package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

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
    public Shipment create(Shipment entity) {
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
        return newShipment;
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
        if (entity.getShipmentId() == null) {
            return Optional.empty();
        }

        if (shipmentStorage.containsKey(entity.getShipmentId())) {
            shipmentStorage.put(entity.getShipmentId(), entity);
            return Optional.of(entity);
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

//    /**
//     * Найти просроченные поставки ???
//     */
//    @Override
//    public List<Shipment> findExpiredShipments(LocalDateTime currentDate) {
//        return shipmentStorage.values().stream()
//                .filter(shipment -> shipment.getExpiryDate() != null)
//                .filter(shipment -> shipment.getExpiryDate().isBefore(currentDate))
//                .collect(Collectors.toList());
//    }

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

//    /**
//     * Найти поставки с наибольшей маржой (разница между продажной и закупочной ценой) ???
//     */
//    @Override
//    public List<Shipment> findTopProfitableShipments(int limit) {
//        return shipmentStorage.values().stream()
//                .sorted((s1, s2) -> Integer.compare(
//                        calculateMargin(s2), calculateMargin(s1)
//                ))
//                .limit(limit)
//                .collect(Collectors.toList());
//    }

//    /**
//     * Найти поставки с наименьшей маржой ???
//     */
//    @Override
//    public List<Shipment> findLeastProfitableShipments(int limit) {
//        return shipmentStorage.values().stream()
//                .filter(shipment -> calculateMargin(shipment) >= 0)
//                .sorted(Comparator.comparingInt(this::calculateMargin))
//                .limit(limit)
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * Найти поставки с убыточными ценами (продажная цена меньше закупочной) ???
//     */
//    @Override
//    public List<Shipment> findLossMakingShipments() {
//        return shipmentStorage.values().stream()
//                .filter(shipment -> shipment.getSalePrice() < shipment.getPurchasePrice())
//                .collect(Collectors.toList());
//    }
//
//    // Вспомогательный метод для расчета маржи
//    private int calculateMargin(Shipment shipment) {
//        return shipment.getSalePrice() - shipment.getPurchasePrice();
//    }
}