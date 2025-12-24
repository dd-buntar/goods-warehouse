package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ShipmentRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class ShipmentRepositoryImpl implements ShipmentRepository {
    @Getter(lazy = true)
    private static final ShipmentRepositoryImpl instance = new ShipmentRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByProductIdStatement;
    private PreparedStatement findByProductionDateStatement;
    private PreparedStatement findByArrivalDateStatement;

    public ShipmentRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO shipments (product_id, purchase_price, sale_price, " +
                            "production_date, expiry_date, arrival_date) " +
                            "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM shipments " +
                            "WHERE shipment_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM shipments"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM shipments " +
                            "WHERE shipment_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE shipments " +
                            "SET product_id = ?, purchase_price = ?, sale_price = ?, " +
                            "production_date = ?, expiry_date = ?, arrival_date = ? " +
                            "WHERE shipment_id = ?"
            );

            this.findByProductIdStatement = connection.prepareStatement(
                    "SELECT * FROM shipments " +
                            "WHERE product_id = ?"
            );

            this.findByProductionDateStatement = connection.prepareStatement(
                    "SELECT * FROM shipments " +
                            "WHERE production_date = ?"
            );

            this.findByArrivalDateStatement = connection.prepareStatement(
                    "SELECT * FROM shipments " +
                            "WHERE arrival_date = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Shipment> create(Shipment entity) {
        try {
            createStatement.setLong(1, entity.getProductId());
            createStatement.setInt(2, entity.getPurchasePrice());
            createStatement.setInt(3, entity.getSalePrice());
            createStatement.setDate(4, java.sql.Date.valueOf(entity.getProductionDate().toLocalDate()));
            createStatement.setDate(5, java.sql.Date.valueOf(entity.getExpiryDate().toLocalDate()));
            createStatement.setDate(6, java.sql.Date.valueOf(entity.getArrivalDate().toLocalDate()));

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Shipment.builder()
                            .shipmentId(generatedId)
                            .productId(entity.getProductId())
                            .purchasePrice(entity.getPurchasePrice())
                            .salePrice(entity.getSalePrice())
                            .productionDate(entity.getProductionDate())
                            .expiryDate(entity.getExpiryDate())
                            .arrivalDate(entity.getArrivalDate())
                            .build());
                }
            }

            // Если ID нет, возвращаем оригинальный объект без ID
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create shipment", e);
        }
    }

    @Override
    public Optional<Shipment> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Shipment shipment = extractShipment(result);
                    return Optional.of(shipment);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get shipment " + id, e);
        }
    }

    @Override
    public List<Shipment> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractShipmentList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all shipments", e);
        }
    }

    @Override
    public Optional<Shipment> update(Shipment entity) {
        try {
            updateStatement.setLong(1, entity.getProductId());
            updateStatement.setInt(2, entity.getPurchasePrice());
            updateStatement.setInt(3, entity.getSalePrice());
            updateStatement.setDate(4, java.sql.Date.valueOf(entity.getProductionDate().toLocalDate()));
            updateStatement.setDate(5, java.sql.Date.valueOf(entity.getExpiryDate().toLocalDate()));
            updateStatement.setDate(6, java.sql.Date.valueOf(entity.getArrivalDate().toLocalDate()));
            updateStatement.setLong(7, entity.getShipmentId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update shipment " + entity.getShipmentId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete shipment by id " + id, e);
        }
    }

    @Override
    public List<Shipment> findByProductId(Long productId) {
        try {
            findByProductIdStatement.setLong(1, productId);
            try (ResultSet result = findByProductIdStatement.executeQuery()) {
                return extractShipmentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get shipments for product " + productId, e);
        }
    }

    @Override
    public List<Shipment> findByProductionDate(LocalDateTime productionDate) {
        try {
            findByProductionDateStatement.setDate(1, java.sql.Date.valueOf(productionDate.toLocalDate()));
            try (ResultSet result = findByProductionDateStatement.executeQuery()) {
                return extractShipmentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get shipments by production date " + productionDate, e);
        }
    }

    @Override
    public List<Shipment> findByArrivalDate(LocalDateTime arrivalDate) {
        try {
            findByArrivalDateStatement.setDate(1, java.sql.Date.valueOf(arrivalDate.toLocalDate()));
            try (ResultSet result = findByArrivalDateStatement.executeQuery()) {
                return extractShipmentList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get shipments by arrival date " + arrivalDate, e);
        }
    }

    private List<Shipment> extractShipmentList(ResultSet result) throws SQLException {
        List<Shipment> shipments = new ArrayList<>();
        while (result.next()) {
            shipments.add(extractShipment(result));
        }
        return shipments;
    }

    private Shipment extractShipment(ResultSet result) throws SQLException {
        return Shipment.builder()
                .shipmentId(result.getLong("shipment_id"))
                .productId(result.getLong("product_id"))
                .purchasePrice(result.getInt("purchase_price"))
                .salePrice(result.getInt("sale_price"))
                .productionDate(result.getDate("production_date").toLocalDate().atStartOfDay())
                .expiryDate(result.getDate("expiry_date").toLocalDate().atStartOfDay())
                .arrivalDate(result.getDate("arrival_date").toLocalDate().atStartOfDay())
                .build();
    }
}