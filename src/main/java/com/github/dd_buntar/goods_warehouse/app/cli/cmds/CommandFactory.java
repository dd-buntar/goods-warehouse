package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.product.*;
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
            default: return null; // потом будет хелп
        }
    }
}
