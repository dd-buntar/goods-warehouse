package com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
public class FindByIdShipmentCommand implements Command {
    public static final String HELP_MESSAGE = "shipment-findById (id)";

    @Setter
    private String[] args;
    private DomainShipmentService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        Optional<Shipment> shipment = service.findById(id);
        System.out.println(shipment.get());
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
