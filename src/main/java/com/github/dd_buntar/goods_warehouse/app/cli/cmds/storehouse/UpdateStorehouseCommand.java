package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateStorehouseCommand implements Command {
    public static final String NAME = "storehouse-update";
    public static final String HELP_MESSAGE = NAME + " (stockId, shipmentId, quantity, locationId)";

    private DomainStorehouseService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long stockId = Long.parseLong(args[0]);
        final Long shipmentId = Long.parseLong(args[1]);
        final Integer quantity = Integer.parseInt(args[2]);
        final Long locationId = Long.parseLong(args[3]);

        service.update(Storehouse.builder()
                .stockId(stockId)
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
