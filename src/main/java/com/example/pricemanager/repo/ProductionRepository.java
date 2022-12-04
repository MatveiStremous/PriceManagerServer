package com.example.pricemanager.repo;

import com.example.pricemanager.entity.Production;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductionRepository implements Repository {
    public void addNewProduction(Production production) {
        String sqlRequest = "INSERT INTO production (amount, total_costs, date, product_id) " +
                "VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, production.getAmount());
            statement.setDouble(2, production.getTotalCosts());
            statement.setDate(3, Date.valueOf(production.getDate()));
            statement.setInt(4, production.getProductId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteProductionById(Integer id) {
        String sql = "DELETE FROM production " +
                "WHERE production_id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateProduction(Production production) {
        String sqlRequest = "UPDATE production SET" +
                " amount = ?," +
                " total_costs = ?," +
                " date = ?" +
                " WHERE production_id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setInt(1, production.getAmount());
            statement.setDouble(2, production.getTotalCosts());
            statement.setDate(3, Date.valueOf(production.getDate()));
            statement.setInt(4, production.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Production> getProductionsByProductId(int productId) {
        List<Production> productions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM production " +
                    "WHERE product_id = " + productId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                Production productionFromDb = new Production();
                productionFromDb.setId(rs.getInt("production_id"));
                productionFromDb.setAmount(rs.getInt("amount"));
                productionFromDb.setDate(rs.getDate("date").toLocalDate());
                productionFromDb.setProductId(rs.getInt("product_id"));
                productionFromDb.setTotalCosts(rs.getDouble("total_costs"));
                productions.add(productionFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productions;
    }

    public Production getProductionById(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM production " +
                    "WHERE production_id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Production productionFromDb = new Production();
                productionFromDb.setId(rs.getInt("production_id"));
                productionFromDb.setAmount(rs.getInt("amount"));
                productionFromDb.setDate(rs.getDate("date").toLocalDate());
                productionFromDb.setProductId(rs.getInt("product_id"));
                productionFromDb.setTotalCosts(rs.getDouble("total_costs"));
                return productionFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
