package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class UpdateStorehouseCommand implements Command {
    public static final String HELP_MESSAGE = "storehouse-update (stockId, shipmentId, quantity, locationId)";

    @Setter
    private String[] args;
    private DomainStorehouseService service;

    @Override
    public void execute() {
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
}
