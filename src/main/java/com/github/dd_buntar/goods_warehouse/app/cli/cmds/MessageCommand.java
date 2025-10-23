package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

public class MessageCommand implements Command {
    public static final String HELP_MESSAGE =
            "exit - выход" +
                    "\n\nStorageLocation Commands:" +
                    "\nlocation-create (rackNum, shelfNum)" +
                    "\nlocation-deleteById (id)" +
                    "\nlocation-findAll" +
                    "\nlocation-findById (id)" +
                    "\nlocation-update (id, rackNum, shelfNum)" +
                    "\n\nManufacturer Commands:" +
                    "\nmanufacturer-create (name, contactPhone, country)" +
                    "\nmanufacturer-deleteById (id)" +
                    "\nmanufacturer-findById (id)" +
                    "\nmanufacturer-update (id, name, contactPhone, country)" +
                    "\n\nProduct Commands:" +
                    "\nproduct-create (name, manufacturerId, weight, description)" +
                    "\nproduct-deleteById (id)" +
                    "\nproduct-findAll" +
                    "\nproduct-findById (id)" +
                    "\nproduct-update (id, name, manufacturerId, weight, description)" +
                    "\n\nShipment Commands:" +
                    "\nshipment-create (productId, purchasePrice, salePrice, productionDate, expiryDate, arrivalDate)" +
                    "\ndate format YYYY-MM-DDT10:30" +
                    "\nshipment-deleteById (id)" +
                    "\nshipment-findAll" +
                    "\nshipment-findById (id)" +
                    "\nshipment-update (shipmentId, productId, purchasePrice, salePrice, productionDate, expiryDate, arrivalDate)" +
                    "\n\nStorehouse Commands:" +
                    "\nstorehouse-create (shipmentId, quantity, locationId)" +
                    "\nstorehouse-deleteById (id)" +
                    "\nstorehouse-findAll" +
                    "\nstorehouse-findById (id)" +
                    "\nstorehouse-update (stockId, shipmentId, quantity, locationId)";

    @Override
    public void execute() {
        System.out.println(HELP_MESSAGE);
    }

    @Override
    public String getHelp() {
        return HELP_MESSAGE;
    }
}
