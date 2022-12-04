package com.example.pricemanager.repo;

import com.example.pricemanager.entity.Company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository implements Repository {
    public boolean addNewCompany(Company company) {
        if (getCompanyByName(company.getName()) == null) {
            String sqlRequest = "INSERT INTO company (name, balance, user_id) " +
                    "VALUES(?, ?, ?)";
            try {
                PreparedStatement statement = connection.prepareStatement(sqlRequest);
                statement.setString(1, company.getName());
                statement.setDouble(2, company.getBalance());
                statement.setInt(3, company.getUserId());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }

    public Company getCompanyById(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM company " +
                    "WHERE company_id = " + id;
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Company companyFromDb = new Company();
                companyFromDb.setId(rs.getInt("company_id"));
                companyFromDb.setBalance(rs.getFloat("balance"));
                companyFromDb.setName(rs.getString("name"));
                companyFromDb.setUserId(rs.getInt("user_id"));
                companyFromDb.setAmountOfProducts(rs.getInt("amount_of_products"));
                return companyFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Company getCompanyByName(String name) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM company " +
                    "WHERE name = '" + name + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                Company companyFromDb = new Company();
                companyFromDb.setId(rs.getInt("company_id"));
                companyFromDb.setBalance(rs.getFloat("balance"));
                companyFromDb.setName(rs.getString("name"));
                companyFromDb.setUserId(rs.getInt("user_id"));
                companyFromDb.setAmountOfProducts(rs.getInt("amount_of_products"));
                return companyFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Company> getCompaniesByUserId(int userId) {
        List<Company> companies = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM company " +
                    "WHERE user_id = " + userId;
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                Company companyFromDb = new Company();
                companyFromDb.setId(rs.getInt("company_id"));
                companyFromDb.setBalance(rs.getFloat("balance"));
                companyFromDb.setName(rs.getString("name"));
                companyFromDb.setUserId(rs.getInt("user_id"));
                companyFromDb.setAmountOfProducts(rs.getInt("amount_of_products"));

                companies.add(companyFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return companies;
    }

    public void deleteCompanyById(int id) {
        String sql = "DELETE FROM company " +
                "WHERE company_id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateCompany(Company company) {
        Company companyFromDb = getCompanyByName(company.getName());
        if (companyFromDb == null || companyFromDb.getId() == company.getId()) {
            String sqlRequest = "UPDATE company SET" +
                    " name = ?," +
                    " balance = ?," +
                    " amount_of_products = ?" +
                    " WHERE company_id = ?";

            try {
                PreparedStatement statement = connection.prepareStatement(sqlRequest);
                statement.setString(1, company.getName());
                statement.setDouble(2, company.getBalance());
                statement.setInt(3, company.getAmountOfProducts());
                statement.setInt(4, company.getId());

                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        return false;
    }
}
