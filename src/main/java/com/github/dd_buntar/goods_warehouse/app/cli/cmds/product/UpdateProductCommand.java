package com.github.dd_buntar.goods_warehouse.app.cli.cmds.product;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateProductCommand implements Command {
    public static final String NAME = "product-update";
    public static final String HELP_MESSAGE = NAME + " (id, name, manufacturerId, weight, description)";

    private DomainProductService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 5) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long id = Long.parseLong(args[0]);
        final String name = args[1];
        final Long manufacturerId = Long.parseLong(args[2]);
        final Integer weight = Integer.parseInt(args[3]);
        final String description = args[4];

        service.update(Product.builder()
                .productId(id)
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
