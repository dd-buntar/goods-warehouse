package com.github.dd_buntar.goods_warehouse.domain.repositories.inmemory;

import com.github.dd_buntar.goods_warehouse.domain.repositories.Storage;
import lombok.Getter;

@Getter
public class InMemoryStorage implements Storage {
    private static InMemoryStorage INSTANCE;

    private final ManufacturerRepositoryImpl manufacturerRepository;
    private final ProductRepositoryImpl productRepository;
    private final ShipmentRepositoryImpl shipmentRepository;
    private final StorehouseRepositoryImpl storehouseRepository;
    private final StorageLocationRepositoryImpl storageLocationRepository;

    private InMemoryStorage() {
        manufacturerRepository = new ManufacturerRepositoryImpl();
        productRepository = new ProductRepositoryImpl();
        shipmentRepository = new ShipmentRepositoryImpl();
        storageLocationRepository = new StorageLocationRepositoryImpl();
        storehouseRepository = new StorehouseRepositoryImpl();
    }

    public static InMemoryStorage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InMemoryStorage();
        }
        return INSTANCE;
    }
}
