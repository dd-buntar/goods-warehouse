package com.github.dd_buntar.goods_warehouse.domain.repositories;

public interface Storage {
    ManufacturerRepository getManufacturerRepository();
    ProductRepository getProductRepository();
    ShipmentRepository getShipmentRepository();
    StorehouseRepository getStorehouseRepository();
    StorageLocationRepository getStorageLocationRepository();
}
