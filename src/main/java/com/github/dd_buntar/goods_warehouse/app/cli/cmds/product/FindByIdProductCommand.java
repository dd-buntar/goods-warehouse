package com.github.dd_buntar.goods_warehouse.app.cli.cmds.product;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
public class FindByIdProductCommand implements Command {
    public static final String HELP_MESSAGE = "product-findById (id)";

    @Setter
    private String[] args;
    private DomainProductService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Optional<Product> product = service.findById(id);
        System.out.println(product.get());
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
