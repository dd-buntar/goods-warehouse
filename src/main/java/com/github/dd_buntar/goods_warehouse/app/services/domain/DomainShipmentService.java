package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.app.services.ProductService;
import com.github.dd_buntar.goods_warehouse.app.services.ShipmentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainShipmentService {
    private final ShipmentService shipmentService;
    private final DomainStorehouseService domainStorehouseService;
    private final ProductService productService;

    public Shipment create(final Shipment entity) {
        productService.findById(entity.getProductId());
        return shipmentService.create(entity);
    }

    public Shipment findById(final Long id) {
        return shipmentService.findById(id);
    }

    public List<Shipment> findAll() {
        return shipmentService.findAll();
    }

    public Shipment update(final Shipment entity) {
        productService.findById(entity.getProductId());
        return shipmentService.update(entity);
    }

    public void deleteById(final Long id) {
        List<Storehouse> storehouses = domainStorehouseService.findByShipmentId(id);
        if (!storehouses.isEmpty()) {
            for (Storehouse s : storehouses) {
                domainStorehouseService.deleteById(s.getStockId());
            }
        }
        shipmentService.deleteById(id);
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
}
