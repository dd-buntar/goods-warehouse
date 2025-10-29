package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.app.services.ManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.ProductService;

import java.util.List;
import java.util.Optional;

public class DomainProductService {
    private final ProductService productService;
    private final DomainShipmentService domainShipmentService;
    private final ManufacturerService manufacturerService;

    public DomainProductService(ProductService productService,
                                DomainShipmentService domainShipmentService,
                                ManufacturerService manufacturerService) {
        this.productService = productService;
        this.domainShipmentService = domainShipmentService;
        this.manufacturerService = manufacturerService;
    }

    public Optional<Product> create(final Product entity) {
        validateProductManufacturerRelation(entity, manufacturerService);
        return productService.create(entity);
    }

    public Optional<Product> findById(final Long id) {
        return productService.findById(id);
    }

    public List<Product> findAll() {
        return productService.findAll();
    }

    public Optional<Product> update(final Product entity) {
        validateProductManufacturerRelation(entity, manufacturerService);
        return productService.update(entity);
    }

    public boolean deleteById(final Long id) {
        List<Shipment> shipments = domainShipmentService.findByProductId(id);
        if (!shipments.isEmpty()) {
            for (Shipment s : shipments) {
                domainShipmentService.deleteById(s.getShipmentId());
            }
        }
        return productService.deleteById(id);
    }

    public Optional<Product> findByName(String name) {
        return productService.findByName(name);
    }

    public List<Product> findByManufacturerId(Long manufacturerId) {
        return productService.findByManufacturerId(manufacturerId);
    }

    private void validateProductManufacturerRelation(Product product, ManufacturerService manufacturerService) {
        if (!manufacturerService.findById(product.getManufacturerId()).isPresent()) {
            throw new IllegalArgumentException("Производителя с таким id не существует");
        }
    }
}
