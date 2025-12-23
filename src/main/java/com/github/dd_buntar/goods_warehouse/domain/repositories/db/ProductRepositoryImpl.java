package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Getter;

public class ProductRepositoryImpl implements ProductRepository {
    @Getter(lazy = true)
    private static final ProductRepositoryImpl instance = new ProductRepositoryImpl();

    private Statement statement;
    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findByNameStatement;
    private PreparedStatement findByManufacturerIdStatement;

    public ProductRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.statement = connection.createStatement();

            this.createStatement = connection.prepareStatement(
                    "INSERT INTO products (name, manufacturer_id, weight_grams, description) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT * FROM products " +
                            "WHERE product_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT * FROM products"
            );

            this.deleteByIdStatement = connection.prepareStatement(
                    "DELETE FROM products " +
                            "WHERE product_id = ?"
            );

            this.updateStatement = connection.prepareStatement(
                    "UPDATE products " +
                            "SET name = ?, manufacturer_id = ?, weight_grams = ?, description = ? " +
                            "WHERE product_id = ?"
            );

            this.findByNameStatement = connection.prepareStatement(
                    "SELECT * FROM products " +
                            "WHERE name = ?"
            );

            this.findByManufacturerIdStatement = connection.prepareStatement(
                    "SELECT * FROM products " +
                            "WHERE manufacturer_id = ?"
            );

        } catch (SQLException e) {
            throw new RuntimeException("Exception while preparing statements: " + e.getMessage());
        }
    }

    @Override
    public Optional<Product> create(Product entity) {
        try {
            createStatement.setString(1, entity.getProductName());
            createStatement.setLong(2, entity.getManufacturerId());
            createStatement.setInt(3, entity.getWeight());
            createStatement.setString(4, entity.getDescription());

            int affectedRows = createStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            try (ResultSet generatedKeys = createStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    return Optional.of(Product.builder()
                            .productId(generatedId)
                            .productName(entity.getProductName())
                            .description(entity.getDescription())
                            .weight(entity.getWeight())
                            .manufacturerId(entity.getManufacturerId())
                            .build());
                }
            }

            // Если ID нет, возвращаем оригинальный объект без ID
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create product", e);
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            findByIdStatement.setLong(1, id);
            try (ResultSet result = findByIdStatement.executeQuery()) {
                if (result.next()) {
                    Product product = extractProduct(result);
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get product " + id, e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (ResultSet result = findAllStatement.executeQuery()) {
            return extractProductList(result);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all products", e);
        }
    }

    @Override
    public Optional<Product> update(Product entity) {
        try {
            updateStatement.setString(1, entity.getProductName());
            updateStatement.setLong(2, entity.getManufacturerId());
            updateStatement.setInt(3, entity.getWeight());
            updateStatement.setString(4, entity.getDescription());
            updateStatement.setLong(5, entity.getProductId());

            int updatedRows = updateStatement.executeUpdate();
            if (updatedRows == 0) {
                return Optional.empty();
            }
            return Optional.of(entity);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update product " + entity.getProductId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            deleteByIdStatement.setLong(1, id);
            int deletedRows = deleteByIdStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete product by id " + id, e);
        }
    }

    @Override
    public Optional<Product> findByName(String name) {
        try {
            findByNameStatement.setString(1, name);
            try (ResultSet result = findByNameStatement.executeQuery()) {
                if (result.next()) {
                    Product product = extractProduct(result);
                    return Optional.of(product);
                } else {
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get product " + name, e);
        }
    }

    @Override
    public List<Product> findByManufacturerId(Long manufacturerId) {
        try {
            findByManufacturerIdStatement.setLong(1, manufacturerId);
            try (ResultSet result = findByManufacturerIdStatement.executeQuery()) {
                return extractProductList(result);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get products with manufacturer " + manufacturerId, e);
        }
    }

    private List<Product> extractProductList(ResultSet result) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (result.next()) {
            products.add(extractProduct(result));
        }
        return products;
    }

    private Product extractProduct(ResultSet result) throws SQLException {
        return Product.builder()
                .productId(result.getLong("product_id"))
                .productName(result.getString("name"))
                .manufacturerId(result.getLong("manufacturer_id"))
                .weight(result.getInt("weight_grams"))
                .description(result.getString("description"))
                .build();
    }
}
