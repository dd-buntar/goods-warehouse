package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class CreateStorehouseCommand implements Command {
    public static final String HELP_MESSAGE = "storehouse-create (shipmentId, quantity, locationId)";

    @Setter
    private String[] args;
    private DomainStorehouseService service;

    @Override
    public void execute() {
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
}
