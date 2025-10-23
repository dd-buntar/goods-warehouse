package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.location.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.product.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse.*;
import com.github.dd_buntar.goods_warehouse.app.services.domain.*;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class CommandFactory {
    private DomainManufacturerService manufacturerService;
    private DomainProductService productService;
    private DomainStorehouseService storehouseService;
    private DomainShipmentService shipmentService;
    private DomainStorageLocationService storageLocationService;

    public Command create(String[] args) {
        String commandName = args[0];
        String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (commandName) {
            case "manufacturer-create":
                return new CreateManufacturerCommand(commandArgs, manufacturerService);
            case "manufacturer-findAll":
                return new FindAllManufacturerCommand(commandArgs, manufacturerService);
            case "manufacturer-deleteById":
                return new DeleteByIdManufacturerCommand(commandArgs, manufacturerService);
            case "manufacturer-findById":
                return new FindByIdManufacturerCommand(commandArgs, manufacturerService);
            case "manufacturer-update":
                return new UpdateManufacturerCommand(commandArgs, manufacturerService);

            case "product-create":
                return new CreateProductCommand(commandArgs, productService);
            case "product-findAll":
                return new FindAllProductCommand(commandArgs, productService);
            case "product-deleteById":
                return new DeleteByIdProductCommand(commandArgs, productService);
            case "product-findById":
                return new FindByIdProductCommand(commandArgs, productService);
            case "product-update":
                return new UpdateProductCommand(commandArgs, productService);

            case "shipment-create":
                return new CreateShipmentCommand(commandArgs, shipmentService);
            case "shipment-findAll":
                return new FindAllShipmentCommand(commandArgs, shipmentService);
            case "shipment-deleteById":
                return new DeleteByIdShipmentCommand(commandArgs, shipmentService);
            case "shipment-findById":
                return new FindByIdShipmentCommand(commandArgs, shipmentService);
            case "shipment-update":
                return new UpdateShipmentCommand(commandArgs, shipmentService);

            case "location-create":
                return new CreateStorageLocationCommand(commandArgs, storageLocationService);
            case "location-findAll":
                return new FindAllStorageLocationCommand(commandArgs, storageLocationService);
            case "location-deleteById":
                return new DeleteByIdStorageLocationCommand(commandArgs, storageLocationService);
            case "location-findById":
                return new FindByIdStorageLocationCommand(commandArgs, storageLocationService);
            case "location-update":
                return new UpdateStorageLocationCommand(commandArgs, storageLocationService);

            case "storehouse-create":
                return new CreateStorehouseCommand(commandArgs, storehouseService);
            case "storehouse-findAll":
                return new FindAllStorehouseCommand(commandArgs, storehouseService);
            case "storehouse-deleteById":
                return new DeleteByIdStorehouseCommand(commandArgs, storehouseService);
            case "storehouse-findById":
                return new FindByIdStorehouseCommand(commandArgs, storehouseService);
            case "storehouse-update":
                return new UpdateStorehouseCommand(commandArgs, storehouseService);

            case "exit":
                break;

            default:
                return new MessageCommand();
        }
        return null;
    }
}
