package com.example.pricemanager.repo;

import com.example.pricemanager.entity.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository {
    public void addNewProduct(Product product) {
        String sqlRequest = "INSERT INTO product (name, company_id) " +
                "VALUES(?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCompanyId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM product " +
                    "WHERE product_id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Product productFromDb = new Product();
                productFromDb.setId(rs.getInt("product_id"));
                productFromDb.setAverageCost(rs.getFloat("average_cost"));
                productFromDb.setName(rs.getString("name"));
                productFromDb.setCompanyId(rs.getInt("company_id"));
                productFromDb.setAverageSellingPrice(rs.getFloat("average_selling_price"));
                productFromDb.setAmount(rs.getInt("amount"));
                return productFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Product> getProductsByCompanyId(int companyId) {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM product " +
                    "WHERE company_id = " + companyId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                Product productFromDb = new Product();
                productFromDb.setId(rs.getInt("product_id"));
                productFromDb.setAverageCost(rs.getFloat("average_cost"));
                productFromDb.setName(rs.getString("name"));
                productFromDb.setCompanyId(rs.getInt("company_id"));
                productFromDb.setAverageSellingPrice(rs.getFloat("average_selling_price"));
                productFromDb.setAmount(rs.getInt("amount"));

                products.add(productFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    public void deleteProductById(int id) {
        String sql = "DELETE FROM product " +
                "WHERE product_id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduct(Product product) {
        String sqlRequest = "UPDATE product SET" +
                " name = ?," +
                " amount = ?," +
                " average_cost = ?," +
                " average_selling_price = ?" +
                " WHERE product_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getAmount());
            statement.setDouble(3, product.getAverageCost());
            statement.setDouble(4, product.getAverageSellingPrice());
            statement.setInt(5, product.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
