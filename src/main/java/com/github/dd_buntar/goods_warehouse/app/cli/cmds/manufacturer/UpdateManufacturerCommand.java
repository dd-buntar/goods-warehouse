package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class UpdateManufacturerCommand implements Command {
    public static final String HELP_MESSAGE = "manufacturer-update (id, name, contactPhone, country)";

    @Setter
    private String[] args;
    private DomainManufacturerService service;

    @Override
    public void execute() {
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
}
