package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorehouseRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class StorehouseRepositoryImpl implements StorehouseRepository {
    @Getter(lazy = true)
    private static final StorehouseRepositoryImpl instance = new StorehouseRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByLocationIdStatement;
    private PreparedStatement findByShipmentIdStatement;

    public StorehouseRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO storehouse (shipment_id, quantity, location_id) " +
                            "VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM storehouse " +
                            "WHERE stock_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM storehouse"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM storehouse " +
                            "WHERE stock_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE storehouse " +
                            "SET shipment_id = ?, quantity = ?, location_id = ? " +
                            "WHERE stock_id = ?"
            );

            this.findByLocationIdStatement = connection.prepareStatement(
                    "SELECT * FROM storehouse " +
                            "WHERE location_id = ?"
            );

            this.findByShipmentIdStatement = connection.prepareStatement(
                    "SELECT * FROM storehouse " +
                            "WHERE shipment_id = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Storehouse> create(Storehouse entity) {
        try {
            createStatement.setLong(1, entity.getShipmentId());
            createStatement.setInt(2, entity.getQuantity());
            createStatement.setLong(3, entity.getLocationId());

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Storehouse.builder()
                            .stockId(generatedId)
                            .shipmentId(entity.getShipmentId())
                            .quantity(entity.getQuantity())
                            .locationId(entity.getLocationId())
                            .build());
                }
            }

            // Если ID нет, возвращаем оригинальный объект без ID
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create storehouse record", e);
        }
    }

    @Override
    public Optional<Storehouse> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Storehouse storehouse = extractStorehouse(result);
                    return Optional.of(storehouse);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get storehouse record " + id, e);
        }
    }

    @Override
    public List<Storehouse> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractStorehouseList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all storehouse records", e);
        }
    }

    @Override
    public Optional<Storehouse> update(Storehouse entity) {
        try {
            updateStatement.setLong(1, entity.getShipmentId());
            updateStatement.setInt(2, entity.getQuantity());
            updateStatement.setLong(3, entity.getLocationId());
            updateStatement.setLong(4, entity.getStockId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update storehouse record " + entity.getStockId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete storehouse record by id " + id, e);
        }
    }

    @Override
    public List<Storehouse> findByLocationId(Long locationId) {
        try {
            findByLocationIdStatement.setLong(1, locationId);
            try (ResultSet result = findByLocationIdStatement.executeQuery()) {
                return extractStorehouseList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get storehouse records for location " + locationId, e);
        }
    }

    @Override
    public List<Storehouse> findByShipmentId(Long shipmentId) {
        try {
            findByShipmentIdStatement.setLong(1, shipmentId);
            try (ResultSet result = findByShipmentIdStatement.executeQuery()) {
                return extractStorehouseList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get storehouse records for shipment " + shipmentId, e);
        }
    }

    private List<Storehouse> extractStorehouseList(ResultSet result) throws SQLException {
        List<Storehouse> storehouseRecords = new ArrayList<>();
        while (result.next()) {
            storehouseRecords.add(extractStorehouse(result));
        }
        return storehouseRecords;
    }

    private Storehouse extractStorehouse(ResultSet result) throws SQLException {
        return Storehouse.builder()
                .stockId(result.getLong("stock_id"))
                .shipmentId(result.getLong("shipment_id"))
                .quantity(result.getInt("quantity"))
                .locationId(result.getLong("location_id"))
                .build();
    }
}