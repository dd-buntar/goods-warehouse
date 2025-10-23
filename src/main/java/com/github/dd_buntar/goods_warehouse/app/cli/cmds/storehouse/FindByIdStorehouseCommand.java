package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
public class FindByIdStorehouseCommand implements Command {
    public static final String HELP_MESSAGE = "storehouse-findById (id)";

    @Setter
    private String[] args;
    private DomainStorehouseService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Optional<Storehouse> storehouse = service.findById(id);
        System.out.println(storehouse.get());
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

}
