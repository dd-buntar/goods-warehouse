package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class FindAllStorehouseCommand implements Command {
    public static final String HELP_MESSAGE = "storehouse-findAll";

    @Setter
    private String[] args;
    private DomainStorehouseService service;

    @Override
    public void execute() {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Storehouse> storehouses = service.findAll();
        storehouses.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
