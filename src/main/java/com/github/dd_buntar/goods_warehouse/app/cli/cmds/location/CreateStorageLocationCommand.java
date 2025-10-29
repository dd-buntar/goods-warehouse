package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class CreateStorageLocationCommand implements Command {
    public static final String HELP_MESSAGE = "location-create (rackNum, shelfNum)";

    @Setter
    private String[] args;
    private DomainStorageLocationService service;

    @Override
    public void execute() {
        if (args.length != 2) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Integer rackNum = Integer.parseInt(args[0]);
        final Integer shelfNum = Integer.parseInt(args[1]);

        service.create(StorageLocation.builder()
                .rackNum(rackNum)
                .shelfNum(shelfNum)
                .build()
        );
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
