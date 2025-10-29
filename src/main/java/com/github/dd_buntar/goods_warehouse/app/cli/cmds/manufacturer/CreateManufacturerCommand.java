package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class CreateManufacturerCommand implements Command {
    public static final String HELP_MESSAGE = "manufacturer-create (name, contactPhone, country)";

    @Setter
    private String[] args;
    private DomainManufacturerService service;

    @Override
    public void execute() {
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
}
