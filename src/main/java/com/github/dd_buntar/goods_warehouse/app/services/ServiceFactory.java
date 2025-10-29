package com.github.dd_buntar.goods_warehouse.app.services;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.repositories.Storage;
import lombok.Getter;

@Getter
public class ServiceFactory {
    private final DomainManufacturerService domainManufacturerService;
    private final DomainProductService domainProductService;
    private final DomainShipmentService domainShipmentService;
    private final DomainStorehouseService domainStorehouseService;
    private final DomainStorageLocationService domainStorageLocationService;

    public ServiceFactory(Storage storage) {
        ManufacturerService manufacturerService = new ManufacturerService(storage.getManufacturerRepository());
        ProductService productService = new ProductService(storage.getProductRepository());
        ShipmentService shipmentService = new ShipmentService(storage.getShipmentRepository());
        StorageLocationService storageLocationService = new StorageLocationService(storage.getStorageLocationRepository());
        StorehouseService storehouseService = new StorehouseService(storage.getStorehouseRepository());

        this.domainStorehouseService = new DomainStorehouseService(storehouseService, shipmentService, storageLocationService);
        this.domainShipmentService = new DomainShipmentService(shipmentService, domainStorehouseService, productService);
        this.domainProductService = new DomainProductService(productService, domainShipmentService, manufacturerService);
        this.domainManufacturerService = new DomainManufacturerService(manufacturerService, domainProductService);
        this.domainStorageLocationService = new DomainStorageLocationService(storageLocationService, domainStorehouseService);
    }
}
