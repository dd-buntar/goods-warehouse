package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateStorehouseCommand implements Command {
    public static final String NAME = "storehouse-create";
    public static final String HELP_MESSAGE = NAME + " (shipmentId, quantity, locationId)";

    private DomainStorehouseService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long shipmentId = Long.parseLong(args[0]);
        final Integer quantity = Integer.parseInt(args[1]);
        final Long locationId = Long.parseLong(args[2]);

        service.create(Storehouse.builder()
                .shipmentId(shipmentId)
                .quantity(quantity)
                .locationId(locationId)
                .build()
        );
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
