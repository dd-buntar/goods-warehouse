package com.github.dd_buntar.goods_warehouse.app.cli;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.cli.cmds.CommandFactory;
import com.github.dd_buntar.goods_warehouse.app.services.domain.*;
import lombok.AllArgsConstructor;

import java.util.Scanner;

@AllArgsConstructor
public class CliApp {
    private DomainManufacturerService manufacturerService;
    private DomainProductService productService;
    private DomainStorehouseService storehouseService;
    private DomainShipmentService shipmentService;
    private DomainStorageLocationService storageLocationService;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        CommandFactory commandFactory = new CommandFactory(manufacturerService,
                productService, storehouseService, shipmentService, storageLocationService);

        while(true) {
            Command cmd = null;
            try {
                System.out.print("> ");
                final String input = scanner.nextLine();
                String[] args = input.split(" ");
                cmd = commandFactory.create(args);
                cmd.execute();
            } catch (Exception e) {
                assert cmd != null;
                System.out.printf("Error: %s\n%s\n", e.getMessage(), cmd.getHelp());
            }
        }
    }
}
