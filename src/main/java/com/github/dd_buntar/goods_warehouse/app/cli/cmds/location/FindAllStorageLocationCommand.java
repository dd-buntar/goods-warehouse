package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class FindAllStorageLocationCommand implements Command {
    public static final String HELP_MESSAGE = "location-findAll";

    @Setter
    private String[] args;
    private DomainStorageLocationService service;

    @Override
    public void execute() {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<StorageLocation> storageLocations = service.findAll();
        storageLocations.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
