package com.example.pricemanager.repo;

import com.example.pricemanager.entity.Sale;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepository implements Repository {
    public void addNewSale(Sale sale) {
        String sqlRequest = "INSERT INTO sale (amount, total_price, date, product_id) " +
                "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, sale.getAmount());
            statement.setDouble(2, sale.getTotalPrice());
            statement.setDate(3, Date.valueOf(sale.getDate()));
            statement.setInt(4, sale.getProductId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteSaleById(Integer id) {
        String sql = "DELETE FROM sale " +
                "WHERE sale_id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateSale(Sale sale) {
        String sqlRequest = "UPDATE sale SET" +
                " amount = ?," +
                " total_price = ?," +
                " date = ?" +
                " WHERE sale_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, sale.getAmount());
            statement.setDouble(2, sale.getTotalPrice());
            statement.setDate(3, Date.valueOf(sale.getDate()));
            statement.setInt(4, sale.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Sale> getSalesByProductId(int productId) {
        List<Sale> sales = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM sale " +
                    "WHERE product_id = " + productId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                Sale saleFromDb = new Sale();
                saleFromDb.setId(rs.getInt("Sale_id"));
                saleFromDb.setAmount(rs.getInt("amount"));
                saleFromDb.setDate(rs.getDate("date").toLocalDate());
                saleFromDb.setProductId(rs.getInt("product_id"));
                saleFromDb.setTotalPrice(rs.getFloat("total_price"));
                sales.add(saleFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sales;
    }

    public Sale getSaleById(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM sale " +
                    "WHERE sale_id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Sale saleFromDb = new Sale();
                saleFromDb.setId(rs.getInt("Sale_id"));
                saleFromDb.setAmount(rs.getInt("amount"));
                saleFromDb.setDate(rs.getDate("date").toLocalDate());
                saleFromDb.setProductId(rs.getInt("product_id"));
                saleFromDb.setTotalPrice(rs.getFloat("total_price"));
                return saleFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
