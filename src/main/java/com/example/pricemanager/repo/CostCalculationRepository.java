package com.example.pricemanager.repo;

import com.example.pricemanager.entity.CostCalculation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CostCalculationRepository implements Repository {
    public void addNewCalculation(CostCalculation costCalculation) {
        String sqlRequest = "INSERT INTO cost_calculation (materials, production, deprecation, salary, others,user_id) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setDouble(1, costCalculation.getMaterials());
            statement.setDouble(2, costCalculation.getProduction());
            statement.setDouble(3, costCalculation.getDeprecation());
            statement.setDouble(4, costCalculation.getSalary());
            statement.setDouble(5, costCalculation.getOthers());
            statement.setInt(6, costCalculation.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

                calculations.add(costCalculationFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return calculations;
    }

    public void deleteAllCalculationsByUserId(int userId) {
        String sql = "DELETE FROM cost_calculation " +
                "WHERE user_id = " + userId;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
