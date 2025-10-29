package com.github.dd_buntar.goods_warehouse.app.cli.cmds.location;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class DeleteByIdStorageLocationCommand implements Command {
    public static final String HELP_MESSAGE = "location-deleteById (id)";

    @Setter
    private String[] args;
    private DomainStorageLocationService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        service.deleteById(id);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
