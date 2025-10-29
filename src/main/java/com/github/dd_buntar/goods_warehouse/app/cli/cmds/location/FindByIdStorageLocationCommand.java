package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindByIdStorageLocationCommand implements Command {
    public static final String NAME = "location-findById";
    public static final String HELP_MESSAGE = NAME + " (id)";

    private DomainStorageLocationService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        StorageLocation storageLocation = service.findById(id);
        System.out.println(storageLocation);
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
