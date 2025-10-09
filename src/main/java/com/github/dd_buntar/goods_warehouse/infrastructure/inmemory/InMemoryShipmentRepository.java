package com.github.dd_buntar.goods_warehouse.infrastructure.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ShipmentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryShipmentRepository implements ShipmentRepository {
    private final Map<Long, Shipment> shipmentStorage = new HashMap<>();

    @Override
    public Shipment create(Shipment entity) {
        return null;
    }

    @Override
    public Optional<Shipment> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Shipment> findAll() {
        return null;
    }

    @Override
    public Optional<Shipment> update(Shipment entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }
}
