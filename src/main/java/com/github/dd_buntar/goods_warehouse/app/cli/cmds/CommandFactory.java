package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.location.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.product.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment.*;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse.*;
import com.github.dd_buntar.goods_warehouse.app.services.ServiceFactory;
import com.github.dd_buntar.goods_warehouse.app.services.domain.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class CommandFactory {
    private List<Command> commands;
    private MessageCommand helpCommand = new MessageCommand("help", "");

    public CommandFactory(ServiceFactory serviceFactory) {
        DomainManufacturerService manufacturerService = serviceFactory.getDomainManufacturerService();
        DomainProductService productService = serviceFactory.getDomainProductService();
        DomainShipmentService shipmentService = serviceFactory.getDomainShipmentService();
        DomainStorehouseService storehouseService = serviceFactory.getDomainStorehouseService();
        DomainStorageLocationService storageLocationService = serviceFactory.getDomainStorageLocationService();

        this.commands = new ArrayList<>();

        this.commands.add(new CreateManufacturerCommand(manufacturerService));
        this.commands.add(new FindAllManufacturerCommand(manufacturerService));
        this.commands.add(new DeleteByIdManufacturerCommand(manufacturerService));
        this.commands.add(new FindByIdManufacturerCommand(manufacturerService));
        this.commands.add(new UpdateManufacturerCommand(manufacturerService));

        this.commands.add(new CreateProductCommand(productService));
        this.commands.add(new FindAllProductCommand(productService));
        this.commands.add(new DeleteByIdProductCommand(productService));
        this.commands.add(new FindByIdProductCommand(productService));
        this.commands.add(new UpdateProductCommand(productService));

        this.commands.add(new CreateShipmentCommand(shipmentService));
        this.commands.add(new FindAllShipmentCommand(shipmentService));
        this.commands.add(new DeleteByIdShipmentCommand(shipmentService));
        this.commands.add(new FindByIdShipmentCommand(shipmentService));
        this.commands.add(new UpdateShipmentCommand(shipmentService));

        this.commands.add(new CreateStorageLocationCommand(storageLocationService));
        this.commands.add(new FindAllStorageLocationCommand(storageLocationService));
        this.commands.add(new DeleteByIdStorageLocationCommand(storageLocationService));
        this.commands.add(new FindByIdStorageLocationCommand(storageLocationService));
        this.commands.add(new UpdateStorageLocationCommand(storageLocationService));

        this.commands.add(new CreateStorehouseCommand(storehouseService));
        this.commands.add(new FindAllStorehouseCommand(storehouseService));
        this.commands.add(new DeleteByIdStorehouseCommand(storehouseService));
        this.commands.add(new FindByIdStorehouseCommand(storehouseService));
        this.commands.add(new UpdateStorehouseCommand(storehouseService));

        this.commands.add(new ExitCommand());
        this.commands.add(this.helpCommand);

        this.updateHelpMsg();
    }

    public Command recognize(String commandName) {
        for (Command command : commands) {
            if (command.getName().equals(commandName)) {
                return command;
            }
        }

        return null;
    }

    private void updateHelpMsg() {
        StringBuilder sb = new StringBuilder();

        sb.append("List of commands:\n");

        for (Command command : commands) {
            sb.append("\t");
            sb.append(command.getHelp());
            sb.append("\n");
        }

        sb.deleteCharAt(sb.length() - 1);
        helpCommand.setMsg(sb.toString());
    }
}
