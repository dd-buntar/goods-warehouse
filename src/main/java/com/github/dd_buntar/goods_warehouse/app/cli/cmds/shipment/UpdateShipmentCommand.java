package com.github.dd_buntar.goods_warehouse.app.cli.cmds.shipment;

import com.github.dd_buntar.goods_warehouse.app.cli.cmds.Command;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateShipmentCommand implements Command {
    public static final String NAME = "shipment-update";
    public static final String HELP_MESSAGE = NAME + " (shipmentId, productId, " +
            "purchasePrice, salePrice, productionDate, expiryDate, arrivalDate)" +
            "\n date format YYYY-MM-DDT10:30";

    private DomainShipmentService service;

    @Override
    public void execute(String[] args) {
        if (args.length != 7) {
            throw new IllegalArgumentException("Неправильное количество аргументов");
        }

        final Long shipmentId = Long.parseLong(args[0]);
        final Long productId = Long.parseLong(args[1]);
        final Integer purchasePrice = Integer.parseInt(args[2]);
        final Integer salePrice = Integer.parseInt(args[3]);
        final LocalDateTime productionDate = LocalDateTime.parse(args[4]);
        final LocalDateTime expiryDate = LocalDateTime.parse(args[5]);
        final LocalDateTime arrivalDate = LocalDateTime.parse(args[6]);

        service.update(Shipment.builder()
                .shipmentId(shipmentId)
                .productId(productId)
                .purchasePrice(purchasePrice)
                .salePrice(salePrice)
                .productionDate(productionDate)
                .expiryDate(expiryDate)
                .arrivalDate(arrivalDate)
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
