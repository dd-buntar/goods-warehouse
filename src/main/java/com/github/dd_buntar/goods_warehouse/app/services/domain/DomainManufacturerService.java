package com.github.dd_buntar.goods_warehouse.app.services.domain;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.app.services.ManufacturerService;

import java.util.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DomainManufacturerService {
    private final ManufacturerService manufacturerService;
    private final DomainProductService domainProductService;

    public Manufacturer create(final Manufacturer entity) {
        return manufacturerService.create(entity);
    }

    public Manufacturer findById(final Long id) {
        return manufacturerService.findById(id);
    }

    public List<Manufacturer> findAll() {
        return manufacturerService.findAll();
    }

    public Manufacturer update(final Manufacturer entity) {
        return manufacturerService.update(entity);
    }

    public void deleteById(final Long id) {
        List<Product> products = domainProductService.findByManufacturerId(id);
        if (!products.isEmpty()) {
            for (Product p : products) {
                domainProductService.deleteById(p.getProductId());
            }
        }
        manufacturerService.deleteById(id);
    }

    public Manufacturer findByName(String name) {
        return manufacturerService.findByName(name);
    }

    public List<Manufacturer> findByCountry(String country) {
        return manufacturerService.findByCountry(country);
    }

    public Manufacturer findByPhone(String phone) {
        return manufacturerService.findByPhone(phone);
    }

    public Manufacturer updatePhone(Long manufacturerId, String newPhone) {
        manufacturerService.findById(manufacturerId);
        return manufacturerService.updatePhone(manufacturerId, newPhone);
    }
}
