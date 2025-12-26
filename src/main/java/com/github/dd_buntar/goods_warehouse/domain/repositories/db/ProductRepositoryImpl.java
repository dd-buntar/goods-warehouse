package com.github.dd_buntar.goods_warehouse.domain.repositories.db;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.ProductRepository;
import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
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

    private PreparedStatement createStatement;
    private PreparedStatement deleteByIdStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement findByIdStatement;
    private PreparedStatement findAllStatement;
    private PreparedStatement findAllWithQuantityStatement;
    private PreparedStatement findByNameStatement;
    private PreparedStatement findByManufacturerIdStatement;

    public ProductRepositoryImpl() {
        Connection connection = PostgresSQLManager.getInstance().getConnection();
        try {
            this.createStatement = connection.prepareStatement(
                    "INSERT INTO products (name, manufacturer_id, weight_grams, description) " +
                            "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            this.findByIdStatement = connection.prepareStatement(
                    "SELECT " +
                            "p.product_id, " +
                            "p.name AS product_name, " +
                            "p.manufacturer_id, " +
                            "p.weight_grams, " +
                            "p.description " +
                            "FROM products p " +
                            "WHERE product_id = ?");

            this.findAllStatement = connection.prepareStatement(
                    "SELECT " +
                            "p.product_id, " +
                            "p.name AS product_name, " +
                            "p.manufacturer_id, " +
                            "p.weight_grams, " +
                            "p.description " +
                            "FROM products p "
            );

            this.findAllWithQuantityStatement = connection.prepareStatement(
                    "SELECT " +
                            "p.product_id, " +
                            "p.name AS product_name, " +
                            "p.manufacturer_id, " +
                            "m.name AS manufacturer_name, " +
                            "p.weight_grams, " +
                            "p.description, " +
                            "COALESCE(SUM(s.quantity), 0) AS total_quantity " +
                            "FROM products p " +
                            "LEFT JOIN shipments sh ON p.product_id = sh.product_id " +
                            "LEFT JOIN storehouse s ON sh.shipment_id = s.shipment_id " +
                            "LEFT JOIN manufacturers m ON p.manufacturer_id = m.manufacturer_id " +
                            "GROUP BY p.product_id, m.name " +
                            "ORDER BY p.product_id"
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
                    "SELECT " +
                            "p.product_id, " +
                            "p.name AS product_name, " +
                            "p.manufacturer_id, " +
                            "p.weight_grams, " +
                            "p.description " +
                            "FROM products p " +
                            "WHERE name = ?"
            );

            this.findByManufacturerIdStatement = connection.prepareStatement(
                    "SELECT " +
                            "p.product_id, " +
                            "p.name AS product_name, " +
                            "p.manufacturer_id, " +
                            "p.weight_grams, " +
                            "p.description " +
                            "FROM products p " +
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
    public List<ProductWithQuantity> findAllWithQuantity() {
        try (ResultSet result = findAllWithQuantityStatement.executeQuery()) {
            return extractProductWithQuantityList(result);
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
                .productName(result.getString("product_name"))
                .manufacturerId(result.getLong("manufacturer_id"))
                .weight(result.getInt("weight_grams"))
                .description(result.getString("description"))
                .build();
    }

    private List<ProductWithQuantity> extractProductWithQuantityList (ResultSet result)
            throws SQLException {
        List<ProductWithQuantity> products = new ArrayList<>();
        while (result.next()) {
            products.add(extractProductWithQuantity(result));
        }
        return products;
    }

    private ProductWithQuantity extractProductWithQuantity(ResultSet result)
            throws SQLException {
        return ProductWithQuantity.builder()
                .product(extractProduct(result))
                .manufacturerName(result.getString("manufacturer_name"))
                .quantity(result.getInt("total_quantity"))
                .build();
    }
}
