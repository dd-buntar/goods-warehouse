// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
package com.github.dd_buntar.goods_warehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.CliApp;
import com.github.dd_buntar.goods_warehouse.app.services.*;
import com.github.dd_buntar.goods_warehouse.app.services.domain.*;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.*;
import com.github.dd_buntar.goods_warehouse.infrastructure.inmemory.*;

public class Main {
    public static void main(String[] args) {
        ManufacturerRepository manufacturerRepository = new ManufacturerRepositoryImpl();
        ProductRepository productRepository = new ProductRepositoryImpl();
        ShipmentRepository shipmentRepository = new ShipmentRepositoryImpl();
        StorageLocationRepository storageLocationRepository = new StorageLocationRepositoryImpl();
        StorehouseRepository storehouseRepository = new StorehouseRepositoryImpl();

        ManufacturerService manufacturerService = new ManufacturerService(manufacturerRepository);
        ProductService productService = new ProductService(productRepository);
        ShipmentService shipmentService = new ShipmentService(shipmentRepository);
        StorageLocationService storageLocationService = new StorageLocationService(storageLocationRepository);
        StorehouseService storehouseService = new StorehouseService(storehouseRepository);

        DomainStorehouseService domainStorehouseService = new DomainStorehouseService(storehouseService, shipmentService, storageLocationService);
        DomainShipmentService domainShipmentService = new DomainShipmentService(shipmentService, domainStorehouseService, productService);
        DomainProductService domainProductService = new DomainProductService(productService, domainShipmentService, manufacturerService);
        DomainManufacturerService domainManufacturerService = new DomainManufacturerService(manufacturerService, domainProductService);
        DomainStorageLocationService domainStorageLocationService = new DomainStorageLocationService(storageLocationService, domainStorehouseService);

        CliApp cliApp = new CliApp(domainManufacturerService,
                domainProductService, domainStorehouseService,
                domainShipmentService, domainStorageLocationService);

        cliApp.run();
    }
}