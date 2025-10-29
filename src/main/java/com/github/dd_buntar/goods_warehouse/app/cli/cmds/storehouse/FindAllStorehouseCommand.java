package com.github.dd_buntar.goods_warehouse.app.cli.cmds.storehouse;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllStorehouseCommand implements Command {
    public static final String NAME = "storehouse-findAll";

    private DomainStorehouseService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Storehouse> storehouses = service.findAll();
        storehouses.forEach(System.out::println);
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
