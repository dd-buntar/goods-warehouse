package com.github.dd_buntar.goods_warehouse.app.cli.cmds.product;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class FindAllProductCommand implements Command {
    public static final String HELP_MESSAGE = "product-findAll";

    @Setter
    private String[] args;
    private DomainProductService service;

    @Override
    public void execute() {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Product> products = service.findAll();
        products.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
