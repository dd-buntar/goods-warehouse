package com.github.dd_buntar.goods_warehouse.infrastructure.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.infrastructure.services.ManufacturerService;

import java.util.*;

public class DomainManufacturerService {
    private final ManufacturerService manufacturerService;
    private final DomainProductService domainProductService;

    public DomainManufacturerService(ManufacturerService manufacturerService,
                                     DomainProductService domainProductService) {
        this.manufacturerService = manufacturerService;
        this.domainProductService = domainProductService;
    }

    public Optional<Manufacturer> create(final Manufacturer entity) {
        return manufacturerService.create(entity);
    }

    public Optional<Manufacturer> findById(final Long id) {
        return manufacturerService.findById(id);
    }

    public List<Manufacturer> findAll() {
        return manufacturerService.findAll();
    }

    public Optional<Manufacturer> update(final Manufacturer entity) {
        return manufacturerService.update(entity);
    }

    public boolean deleteById(final Long id) {
        List<Product> products = domainProductService.findByManufacturerId(id);
        if (!products.isEmpty()) {
            for (Product p : products) {
                domainProductService.deleteById(p.getProductId());
            }
        }
        return manufacturerService.deleteById(id);
    }

    public Optional<Manufacturer> findByName(String name) {
        return manufacturerService.findByName(name);
    }

    public List<Manufacturer> findByCountry(String country) {
        return manufacturerService.findByCountry(country);
    }

    public Optional<Manufacturer> findByPhone(String phone) {
        return manufacturerService.findByPhone(phone);
    }

    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        Optional<Manufacturer> manufacturer = manufacturerService.findById(manufacturerId);
        if (!manufacturer.isPresent()) {
            throw new IllegalArgumentException("Производителя с таким id не существует");
        }
        return manufacturerService.updatePhone(manufacturerId, newPhone);
    }
}
