package com.github.dd_buntar.goods_warehouse;

import com.github.dd_buntar.goods_warehouse.app.WebServer;
import com.github.dd_buntar.goods_warehouse.app.cli.CliApp;
import com.github.dd_buntar.goods_warehouse.app.services.*;
import com.github.dd_buntar.goods_warehouse.app.services.domain.*;
import com.github.dd_buntar.goods_warehouse.domain.repositories.*;
import com.github.dd_buntar.goods_warehouse.domain.repositories.db.DBStorage;
import com.github.dd_buntar.goods_warehouse.domain.repositories.inmemory.*;

public class Main {
    public static void main(String[] args) {
        DBStorage storage = DBStorage.getInstance();
        ServiceFactory serviceFactory = new ServiceFactory(storage);
//        CliApp cliApp = new CliApp(serviceFactory);
//        cliApp.run();
        try {
            WebServer server = new WebServer(serviceFactory);
            server.start();
        } catch (Exception e) {
            System.out.printf("Error: %s", e.getMessage());
        }
    }
}