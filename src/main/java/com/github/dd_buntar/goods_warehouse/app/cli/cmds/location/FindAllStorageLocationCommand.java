package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllStorageLocationCommand implements Command {
    public static final String NAME = "location-findAll";

    private DomainStorageLocationService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<StorageLocation> storageLocations = service.findAll();
        storageLocations.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return NAME;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
