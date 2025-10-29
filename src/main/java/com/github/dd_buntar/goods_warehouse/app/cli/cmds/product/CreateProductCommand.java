package com.github.dd_buntar.goods_warehouse.app.cli.cmds.product;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateProductCommand implements Command {
    public static final String NAME = "product-create";
    public static final String HELP_MESSAGE = NAME + "product-create (name, manufacturerId, weight, description)";

    private DomainProductService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 4) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final String name = args[0];
        final Long manufacturerId = Long.parseLong(args[1]);
        final Integer weight = Integer.parseInt(args[2]);
        final String description = args[3];

        service.create(Product.builder()
                .productName(name)
                .manufacturerId(manufacturerId)
                .weight(weight)
                .description(description)
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
