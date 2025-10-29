package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateManufacturerCommand implements Command {
    public static final String NAME = "manufacturer-create";
    public static final String HELP_MESSAGE = NAME + " (name, contactPhone, country)";

    private DomainManufacturerService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final String name = args[0];
        final String contactPhone = args[1];
        final String country = args[2];

        service.create(Manufacturer.builder()
                .manufacturerName(name)
                .contactPhone(contactPhone)
                .country(country)
                .build()
        );
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
