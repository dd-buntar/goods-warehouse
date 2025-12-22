package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ManufacturerRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
                            "SET name = ?, country = ?,  contact_phone = ? " +
                            "WHERE manufacturer_id = ?"
            );

            this.findByNameStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers " +
                            "WHERE name = ?"
            );

            this.findByCountryStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers " +
                            "WHERE country = ?"
            );

            this.findByPhoneStatement = connection.prepareStatement(
                    "SELECT * FROM manufacturers " +
                            "WHERE contact_phone = ?"
            );


        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Manufacturer> create(Manufacturer entity) {
        try {
            createStatement.setString(1, entity.getManufacturerName());
            createStatement.setString(2, entity.getCountry());
            createStatement.setString(3, entity.getContactPhone());
            createStatement.executeUpdate();
        } catch (SQLException e) {
            entity = null;
            throw new RuntimeException("Failed to create manufacturer", e);
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {  // проверить, работает или нет ???
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Manufacturer manufacturer = extractManufacturer(result);
                    return Optional.of(manufacturer);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Manufacturer> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractManufacturerList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all manufacturers", e);
        }
    }

    @Override
    public Optional<Manufacturer> update(Manufacturer entity) {
        try {
            updateStatement.setLong(4, entity.getManufacturerId());
            updateStatement.setString(1, entity.getManufacturerName());
            updateStatement.setString(2, entity.getCountry());
            updateStatement.setString(3, entity.getContactPhone());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update manufacturer " + entity.getManufacturerId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            if (deletedRows == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete manufacturer by id " + id, e);
        }
    }

    @Override
    public Optional<Manufacturer> findByName(String name) {
        try {
            findByNameStatement.setString(1, name);
            try (ResultSet result = findByNameStatement.executeQuery()) {
                if (result.next()) {
                    Manufacturer manufacturer = extractManufacturer(result);
                    return Optional.of(manufacturer);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Manufacturer> findByCountry(String country) {
        try {
            findByCountryStatement.setString(1, country);
            try (ResultSet result = findByCountryStatement.executeQuery()) {
                return extractManufacturerList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Manufacturer> findByPhone(String phone) {
        try {
            findByPhoneStatement.setString(1, phone);
            try (ResultSet result = findByPhoneStatement.executeQuery()) {
                if (result.next()) {
                    Manufacturer manufacturer = extractManufacturer(result);
                    return Optional.of(manufacturer);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Manufacturer> updatePhone(Long manufacturerId, String newPhone) {
        Optional<Manufacturer> m = findById(manufacturerId);
        if (m.isPresent()) {
            return update(Manufacturer.builder()
                    .manufacturerId(manufacturerId)
                    .manufacturerName(m.get().getManufacturerName())
                    .country(m.get().getCountry())
                    .contactPhone(newPhone)
                    .build()
            );
        }
        return Optional.empty();
    }

    private List<Manufacturer> extractManufacturerList(ResultSet result) throws SQLException {
        List<Manufacturer> manufacturers = new ArrayList<>();
        while (result.next()) {
            manufacturers.add(extractManufacturer(result));
        }
        return manufacturers;
    }

    private Manufacturer extractManufacturer(ResultSet result) throws SQLException {
        return Manufacturer.builder()
                .manufacturerId(result.getLong("manufacturer_id"))
                .manufacturerName(result.getString("name"))
                .contactPhone(result.getString("contact_phone"))
                .country(result.getString("country"))
                .build();
    }
}
