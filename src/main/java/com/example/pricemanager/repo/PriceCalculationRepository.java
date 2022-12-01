package com.example.pricemanager.repo;

import com.example.pricemanager.entity.PriceCalculation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PriceCalculationRepository implements Repository {
    public double addNewCalculation(PriceCalculation priceCalculation) {
        String sqlRequest = "INSERT INTO price_calculation (average_cost, increase_perc, tax_perc, result, user_id) " +
                "VALUES(?, ?, ?, ?, ?)";
        double result = priceCalculation.getAverageCost() * (priceCalculation.getTaxPerc() / 100 + 1) * (priceCalculation.getIncreasePerc() / 100 + 1);
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setDouble(1, priceCalculation.getAverageCost());
            statement.setFloat(2, priceCalculation.getIncreasePerc());
            statement.setFloat(3, priceCalculation.getTaxPerc());
            statement.setDouble(4, result);
            statement.setInt(5, priceCalculation.getUserId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<PriceCalculation> getCalculationsByUserId(int userId) {
        List<PriceCalculation> calculations = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM price_calculation " +
                    "WHERE user_id = " + userId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                PriceCalculation priceCalculationFromDb = new PriceCalculation();
                priceCalculationFromDb.setId(rs.getInt("calc_id"));
                priceCalculationFromDb.setAverageCost(rs.getDouble("average_cost"));
                priceCalculationFromDb.setIncreasePerc(rs.getFloat("increase_perc"));
                priceCalculationFromDb.setUserId(rs.getInt("user_id"));
                priceCalculationFromDb.setTaxPerc(rs.getFloat("tax_perc"));
                priceCalculationFromDb.setResult(rs.getDouble("result"));

                calculations.add(priceCalculationFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return calculations;
    }

    public void deleteAllCalculationsByUserId(int user_id) {
        String sql = "DELETE FROM price_calculation " +
                "WHERE user_id = " + user_id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
