package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.app.services.ManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.ProductService;

import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainProductService {
    private final ProductService productService;
    private final DomainShipmentService domainShipmentService;
    private final ManufacturerService manufacturerService;

    public Product create(final Product entity) {
        manufacturerService.findById(entity.getManufacturerId());
        return productService.create(entity);
    }

    public Product findById(final Long id) {
        return productService.findById(id);
    }

    public List<Product> findAll() {
        return productService.findAll();
    }

    public List<ProductWithQuantity> findAllWithQuantity() {
        return productService.findAllWithQuantity();
    }

    public Product update(final Product entity) {
        manufacturerService.findById(entity.getManufacturerId());
        return productService.update(entity);
    }

    public void deleteById(final Long id) {
        List<Shipment> shipments = domainShipmentService.findByProductId(id);
        if (!shipments.isEmpty()) {
            for (Shipment s : shipments) {
                domainShipmentService.deleteById(s.getShipmentId());
            }
        }
        productService.deleteById(id);
    }

    public Product findByName(String name) {
        return productService.findByName(name);
    }

    public List<Product> findByManufacturerId(Long manufacturerId) {
        return productService.findByManufacturerId(manufacturerId);
    }
}
