package com.github.dd_buntar.goods_warehouse.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import lombok.Getter;
import lombok.Setter;

public class PostgresSQLManager {
    @Getter(lazy = true)
    private static final PostgresSQLManager instance = new PostgresSQLManager();

    @Setter
    private static String resourceName = "application";

    @Getter
    private Connection connection;

    private PostgresSQLManager() {
        try {
            ResourceBundle rb = ResourceBundle.getBundle(resourceName);
            String url = rb.getString("DATABASE_URL");
            String user = rb.getString("DATABASE_USER");
            String password = rb.getString("DATABASE_PASSWORD");

            connection = DriverManager.getConnection(url, user, password);
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS manufacturers (" +
                            "manufacturer_id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "contact_phone VARCHAR(12) UNIQUE NOT NULL," +
                            "country VARCHAR(50) NOT NULL" +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS products (" +
                            "product_id SERIAL PRIMARY KEY, " +
                            "name VARCHAR(100) NOT NULL, " +
                            "manufacturer_id INTEGER NOT NULL, " +
                            "weight_grams INTEGER NOT NULL CHECK (weight_grams > 0)," +
                            "description TEXT," +
                            "FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(manufacturer_id)" +
                                "ON DELETE CASCADE" +
                                "ON UPDATE CASCADE" +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS storage_location (" +
                            "location_id SERIAL PRIMARY KEY, " +
                            "rack_num INTEGER NOT NULL CHECK (rack_num > 0), " +
                            "shelf_num INTEGER NOT NULL CHECK (shelf_num > 0) " +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS shipments (" +
                            "shipment_id SERIAL PRIMARY KEY, " +
                            "product_id INTEGER NOT NULL, " +
                            "purchase_price DECIMAL(10,2) NOT NULL CHECK (purchase_price >= 0), " +
                            "sale_price DECIMAL(10,2) NOT NULL CHECK (sale_price >= 0)," +
                            "production_date DATE NOT NULL," +
                            "expiry_date DATE NOT NULL," +
                            "arrival_date DATE NOT NULL," +
                            "CONSTRAINT fk_shipments_products" +
                                "FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                                    "ON DELETE CASCADE" +
                                    "ON UPDATE CASCADE," +
                            "CONSTRAINT chk_dates CHECK (production_date <= expiry_date AND arrival_date >= production_date)" +
                            ")"
            );
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS storehouse (" +
                            "stock_id SERIAL PRIMARY KEY, " +
                            "shipment_id INTEGER NOT NULL, " +
                            "quantity INTEGER NOT NULL CHECK (quantity != 0), " +
                            "location_id INTEGER NOT NULL," +
                            "CONSTRAINT fk_storehouse_shipments" +
                                "FOREIGN KEY (shipment_id) REFERENCES shipments(shipment_id)" +
                                    "ON DELETE CASCADE" +
                                    "ON UPDATE CASCADE," +
                            "CONSTRAINT fk_storehouse_location" +
                            "FOREIGN KEY (location_id) REFERENCES storage_location(location_id)" +
                                    "ON DELETE CASCADE" +
                                    "ON UPDATE CASCADE" +
                            ")"
            );
        } catch (java.util.MissingResourceException e) {
            throw new RuntimeException("Cannot load database configuration: resource '" + resourceName + "' not found");
        } catch (SQLException e) {
            throw new RuntimeException("Error while connecting to database and preparing tables: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error initializing DB manager: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Exception during closing connection");
        }
    }
}
