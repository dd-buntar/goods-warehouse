package com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class DeleteByIdShipmentCommand implements Command {
    public static final String HELP_MESSAGE = "shipment-deleteById (id)";

    @Setter
    private String[] args;
    private DomainShipmentService service;

    @Override
    public void execute() {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }
        Long id = Long.parseLong(args[0]);
        service.deleteById(id);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
