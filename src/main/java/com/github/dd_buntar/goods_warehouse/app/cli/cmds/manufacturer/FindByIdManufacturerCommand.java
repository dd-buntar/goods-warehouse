package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.ManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FindByIdManufacturerCommand implements Command {
    public static final String HELP_MESSAGE = "manufacturer-findById (id)";

    @Setter
    private String[] args;
    private DomainManufacturerService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Optional<Manufacturer> manufacturer = service.findById(id);
        System.out.println(manufacturer.get());
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }

}
