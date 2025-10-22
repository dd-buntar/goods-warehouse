package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer.CreateManufacturerCommand;
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
//        Command cmd;
        switch (commandName) {
            case "manufacturer-create":
                return new CreateManufacturerCommand(Arrays.copyOfRange(args, 1, args.length), manufacturerService);
            default: return null; // потом будет хелп
        }
    }
}
