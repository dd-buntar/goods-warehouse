package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class DeleteByIdManufacturerCommand implements Command {
    public static final String HELP_MESSAGE = "manufacturer-deleteById (id)";

    @Setter
    private String[] args;
    private DomainManufacturerService service;

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
