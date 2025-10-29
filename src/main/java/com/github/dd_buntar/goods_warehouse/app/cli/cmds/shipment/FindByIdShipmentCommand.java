package com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindByIdShipmentCommand implements Command {
    public static final String NAME = "shipment-findById";
    public static final String HELP_MESSAGE = NAME + " (id)";

    private DomainShipmentService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Shipment shipment = service.findById(id);
        System.out.println(shipment);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
