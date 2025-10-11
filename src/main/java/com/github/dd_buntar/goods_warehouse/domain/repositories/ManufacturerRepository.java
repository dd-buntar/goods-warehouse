package com.github.dd_buntar.goods_warehouse.domain.repositories;

import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends Repository<Manufacturer, Long>{
    Optional<Manufacturer> findByName(String name);
    List<Manufacturer> findByCountry(String country);
    Optional<Manufacturer> findByPhone(String phone);
    Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone);

    // boolean existsByName(String name);
}
