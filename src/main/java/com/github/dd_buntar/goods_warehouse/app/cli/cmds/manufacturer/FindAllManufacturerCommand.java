package com.github.dd_buntar.goods_warehouse.app.cli.cmds.manufacturer;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllManufacturerCommand implements Command {
    public static final String NAME = "manufacturer-findAll";

    private DomainManufacturerService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Manufacturer> manufacturers = service.findAll();
        manufacturers.forEach(System.out::println);
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
