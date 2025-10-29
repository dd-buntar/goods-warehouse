package com.github.dd_buntar.goods_warehouse.app.cli.cmds.product;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FindAllProductCommand implements Command {
    public static final String NAME = "product-findAll";

    private DomainProductService service;

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Product> products = service.findAll();
        products.forEach(System.out::println);
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
