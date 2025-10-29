package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class UpdateStorageLocationCommand implements Command {
    public static final String NAME = "location-update";
    public static final String HELP_MESSAGE = NAME + " (id, rackNum, shelfNum)";

    private DomainStorageLocationService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);
        final Integer rackNum = Integer.parseInt(args[1]);
        final Integer shelfNum = Integer.parseInt(args[2]);

        service.update(StorageLocation.builder()
                .locationId(id)
                .rackNum(rackNum)
                .shelfNum(shelfNum)
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
