package com.github.dd_buntar.goods_warehouse.app.cli;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.CommandFactory;
import com.github.dd_buntar.goods_warehouse.app.services.ServiceFactory;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CliApp {
    private DomainManufacturerService manufacturerService;
    private DomainProductService productService;
    private DomainStorehouseService storehouseService;
    private DomainShipmentService shipmentService;
    private DomainStorageLocationService storageLocationService;

    private List<Command> commands;

    private final CommandFactory commandFactory;

    public CliApp(ServiceFactory serviceFactory) {
        this.commandFactory = new CommandFactory(serviceFactory);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            Command cmd = null;
            try {
                System.out.print("> ");
                final String input = scanner.nextLine();
                String[] args = input.split(" +");
                String commandName = args[0];
                if (commandName.equals("quit")) {
                    break;
                }
                cmd = commandFactory.recognize(commandName);
                if (cmd == null) {
                    System.out.printf("Command not found: %s\n", commandName);
                    continue;
                }
                String[] commandArgs = Arrays.copyOfRange(args, 1, args.length);
                cmd.execute(commandArgs);
            } catch (Exception e) {
                assert cmd != null;
                System.out.printf("Error: %s\n%s\n", e.getMessage(), cmd.getHelp());
            }
        }
    }
}
