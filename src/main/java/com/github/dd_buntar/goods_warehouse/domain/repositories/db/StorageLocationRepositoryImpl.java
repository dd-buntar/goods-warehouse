package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.repositories.StorageLocationRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.Getter;

public class StorageLocationRepositoryImpl implements StorageLocationRepository {
    @Getter(lazy = true)
    private static final StorageLocationRepositoryImpl instance = new StorageLocationRepositoryImpl();

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByRackNumberStatement;
    private PreparedStatement findAllRackNumbersStatement;
    private PreparedStatement findShelvesByRackStatement;

    public StorageLocationRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO storage_location (rack_num, shelf_num) " +
                            "VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM storage_location " +
                            "WHERE location_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM storage_location"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM storage_location " +
                            "WHERE location_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE storage_location " +
                            "SET rack_num = ?, shelf_num = ? " +
                            "WHERE location_id = ?"
            );

            this.findByRackNumberStatement = connection.prepareStatement(
                    "SELECT * FROM storage_location " +
                            "WHERE rack_num = ?"
            );

            this.findAllRackNumbersStatement = connection.prepareStatement(
                    "SELECT DISTINCT rack_num FROM storage_location ORDER BY rack_num"
            );

            this.findShelvesByRackStatement = connection.prepareStatement(
                    "SELECT shelf_num FROM storage_location " +
                            "WHERE rack_num = ? ORDER BY shelf_num"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<StorageLocation> create(StorageLocation entity) {
        try {
            createStatement.setInt(1, entity.getRackNum());
            createStatement.setInt(2, entity.getShelfNum());

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(StorageLocation.builder()
                            .locationId(generatedId)
                            .rackNum(entity.getRackNum())
                            .shelfNum(entity.getShelfNum())
                            .build());
                }
            }

            // Если ID нет, возвращаем оригинальный объект без ID
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create storage location", e);
        }
    }

    @Override
    public Optional<StorageLocation> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    StorageLocation location = extractStorageLocation(result);
                    return Optional.of(location);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get storage location " + id, e);
        }
    }

    @Override
    public List<StorageLocation> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractStorageLocationList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all storage locations", e);
        }
    }

    @Override
    public Optional<StorageLocation> update(StorageLocation entity) {
        try {
            updateStatement.setInt(1, entity.getRackNum());
            updateStatement.setInt(2, entity.getShelfNum());
            updateStatement.setLong(3, entity.getLocationId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update storage location " + entity.getLocationId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete storage location by id " + id, e);
        }
    }

    @Override
    public List<StorageLocation> findByRackNumber(Integer rackNum) {
        try {
            findByRackNumberStatement.setInt(1, rackNum);
            try (ResultSet result = findByRackNumberStatement.executeQuery()) {
                return extractStorageLocationList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get storage locations for rack " + rackNum, e);
        }
    }

    @Override
    public Set<Integer> findAllRackNumbers() {
        try (ResultSet result = findAllRackNumbersStatement.executeQuery()) {
            Set<Integer> rackNumbers = new HashSet<>();
            while (result.next()) {
                rackNumbers.add(result.getInt("rack_num"));
            }
            return rackNumbers;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all rack numbers", e);
        }
    }

    @Override
    public List<Integer> findShelvesByRack(Integer rackNum) {
        try {
            findShelvesByRackStatement.setInt(1, rackNum);
            try (ResultSet result = findShelvesByRackStatement.executeQuery()) {
                List<Integer> shelves = new ArrayList<>();
                while (result.next()) {
                    shelves.add(result.getInt("shelf_num"));
                }
                return shelves;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get shelves for rack " + rackNum, e);
        }
    }

    private List<StorageLocation> extractStorageLocationList(ResultSet result) throws SQLException {
        List<StorageLocation> locations = new ArrayList<>();
        while (result.next()) {
            locations.add(extractStorageLocation(result));
        }
        return locations;
    }

    private StorageLocation extractStorageLocation(ResultSet result) throws SQLException {
        return StorageLocation.builder()
                .locationId(result.getLong("location_id"))
                .rackNum(result.getInt("rack_num"))
                .shelfNum(result.getInt("shelf_num"))
                .build();
    }
}
