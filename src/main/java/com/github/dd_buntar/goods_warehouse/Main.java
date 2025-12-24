// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
package com.github.dd_buntar.goods_warehouse;

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
        CliApp cliApp = new CliApp(serviceFactory);

        cliApp.run();
    }
}