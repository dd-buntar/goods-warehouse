package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.domain.repositories.Storage;
import lombok.Getter;

@Getter
public class DBStorage implements Storage {
    private static DBStorage INSTANCE;

    private final ManufacturerRepositoryImpl manufacturerRepository;
    private final ProductRepositoryImpl productRepository;
    private final ShipmentRepositoryImpl shipmentRepository;
    private final StorehouseRepositoryImpl storehouseRepository;
    private final StorageLocationRepositoryImpl storageLocationRepository;

    private DBStorage() {
        this.manufacturerRepository = new ManufacturerRepositoryImpl();
        this.productRepository = new ProductRepositoryImpl();
        this.shipmentRepository = new ShipmentRepositoryImpl();
        this.storageLocationRepository = new StorageLocationRepositoryImpl();
        this.storehouseRepository = new StorehouseRepositoryImpl();
    }

    public static DBStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBStorage();
        }
        return INSTANCE;
    }
}
