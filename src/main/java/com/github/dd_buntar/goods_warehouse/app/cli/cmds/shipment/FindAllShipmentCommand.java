package com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class FindAllShipmentCommand implements Command {
    public static final String HELP_MESSAGE = "shipment-findAll";

    @Setter
    private String[] args;
    private DomainShipmentService service;

    @Override
    public void execute() {
        if (args.length > 0) {
            throw new IllegalArgumentException("У команды не должно быть аргументов");
        }
        List<Shipment> shipments = service.findAll();
        shipments.forEach(System.out::println);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
