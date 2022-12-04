package com.example.pricemanager.repo;

import com.example.pricemanager.entity.CostCalculation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CostCalculationRepository implements Repository {
    public double addNewCalculation(CostCalculation costCalculation) {
        String sqlRequest = "INSERT INTO cost_calculation (materials, production, deprecation, salary, others, result, user_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        double result = costCalculation.getMaterials() + costCalculation.getDeprecation() + costCalculation.getProduction() + costCalculation.getSalary() + costCalculation.getOthers();
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setDouble(1, costCalculation.getMaterials());
            statement.setDouble(2, costCalculation.getProduction());
            statement.setDouble(3, costCalculation.getDeprecation());
            statement.setDouble(4, costCalculation.getSalary());
            statement.setDouble(5, costCalculation.getOthers());
            statement.setDouble(6, result);
            statement.setInt(7, costCalculation.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<CostCalculation> getCalculationsByUserId(int userId) {
        List<CostCalculation> calculations = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM cost_calculation " +
                    "WHERE user_id = " + userId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                CostCalculation costCalculationFromDb = new CostCalculation();
                costCalculationFromDb.setId(rs.getInt("calc_id"));
                costCalculationFromDb.setMaterials(rs.getDouble("materials"));
                costCalculationFromDb.setProduction(rs.getDouble("production"));
                costCalculationFromDb.setDeprecation(rs.getDouble("deprecation"));
                costCalculationFromDb.setSalary(rs.getDouble("salary"));
                costCalculationFromDb.setOthers(rs.getDouble("others"));
                costCalculationFromDb.setUserId(rs.getInt("user_id"));
                costCalculationFromDb.setResult(rs.getDouble("result"));

                calculations.add(costCalculationFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return calculations;
    }

    public void deleteAllCalculationsByUserId(int user_id) {
        String sql = "DELETE FROM cost_calculation " +
                "WHERE user_id = " + user_id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}