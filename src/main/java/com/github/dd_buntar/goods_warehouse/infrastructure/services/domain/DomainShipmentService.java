package com.github.dd_buntar.goods_warehouse.infrastructure.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ProductService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ShipmentService;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.StorehouseService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DomainShipmentService {
    private final ShipmentService shipmentService;
    private final DomainStorehouseService domainStorehouseService;
    private final ProductService productService;

    public DomainShipmentService(ShipmentService shipmentService,
                                 DomainStorehouseService domainStorehouseService,
                                 ProductService productService) {
        this.shipmentService = shipmentService;
        this.domainStorehouseService = domainStorehouseService;
        this.productService = productService;
    }

    public Optional<Shipment> create(final Shipment entity) {
        validateShipmentProductRelation(entity, productService);
        return shipmentService.create(entity);
    }

    public Optional<Shipment> findById(final Long id) {
        return shipmentService.findById(id);
    }

    public List<Shipment> findAll() {
        return shipmentService.findAll();
    }

    public Optional<Shipment> update(final Shipment entity) {
        validateShipmentProductRelation(entity, productService);
        return shipmentService.update(entity);
    }

    public boolean deleteById(final Long id) {
        List<Storehouse> storehouses = domainStorehouseService.findByShipmentId(id);
        if (!storehouses.isEmpty()) {
            for (Storehouse s : storehouses) {
                domainStorehouseService.deleteById(s.getStockId());
            }
        }
        return shipmentService.deleteById(id);
    }

    public List<Shipment> findByProductId(Long productId) {
        return shipmentService.findByProductId(productId);
    }

    public List<Shipment> findByProductionDate(LocalDateTime productionDate) {
        return shipmentService.findByProductionDate(productionDate);
    }

    public List<Shipment> findByArrivalDate(LocalDateTime arrivalDate) {
        return shipmentService.findByArrivalDate(arrivalDate);
    }

    private void validateShipmentProductRelation(Shipment shipment, ProductService ps) {
        if (!ps.findById(shipment.getProductId()).isPresent()) {
            throw new IllegalArgumentException("Продукта с таким id не существует");
        }
    }
}
