package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
public class FindByIdStorageLocationCommand implements Command {
    public static final String HELP_MESSAGE = "location-findById (id)";

    @Setter
    private String[] args;
    private DomainStorageLocationService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Optional<StorageLocation> storageLocation = service.findById(id);
        System.out.println(storageLocation.get());
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
