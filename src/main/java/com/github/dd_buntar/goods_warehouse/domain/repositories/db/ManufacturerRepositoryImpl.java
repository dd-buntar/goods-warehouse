package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class ManufacturerRepositoryImpl implements ManufacturerRepository {
    @Getter(lazy = true)
    private static final ManufacturerRepositoryImpl instance = new ManufacturerRepositoryImpl();

    private Statement statement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByNameStatement;
    private PreparedStatement findByCountryStatement;
    private PreparedStatement findByPhoneStatement;

    public ManufacturerRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.statement = connection.createStatement();

            this.createStatement = connection.prepareStatement(
                    "INSERT INTO manufacturers (name, country, contact_phone) " +
                            "VALUES (?, ?, ?)"
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers " +
                            "WHERE manufacturer_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM manufacturers " +
                            "WHERE manufacturer_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE manufacturers " +
                            "SET manufacturer_id = ?, name = ?, country = ?,  contact_phone = ?" +
                            "WHERE id = ?"
            );

            this.findByNameStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers" +
                            "WHERE name = ?"
            );

            this.findByCountryStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers" +
                            "WHERE country = ?"
            );

            this.findByPhoneStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers" +
                            "WHERE contact_phone = ?"
            );


        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Manufacturer> create(Manufacturer entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Manufacturer> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> findAll() {
        return null;
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer entity) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long aLong) {
        return false;
    }

    @Override
    public Optional<Manufacturer> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Manufacturer> findByCountry(String country) {
        return null;
    }

    @Override
    public Optional<Manufacturer> findByPhone(String phone) {
        return Optional.empty();
    }

    @Override
    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        return Optional.empty();
    }
}
