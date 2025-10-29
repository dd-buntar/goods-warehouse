package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateManufacturerCommand implements Command {
    public static final String NAME = "manufacturer-update";
    public static final String HELP_MESSAGE = NAME + " (id, name, contactPhone, country)";

    private DomainManufacturerService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);
        final String name = args[1];
        final String contactPhone = args[2];
        final String country = args[3];

        service.update(Manufacturer.builder()
                .manufacturerId(id)
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
