package com.example.pricemanager.repo;

import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.Product;
import com.example.pricemanager.entity.Production;
import com.example.pricemanager.entity.Sale;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository {
    private static final CompanyRepository companyRepository = new CompanyRepository();
    private static final ProductionRepository productionRepository = new ProductionRepository();
    private static final SaleRepository saleRepository = new SaleRepository();

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

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        companyRepository.updateAmountOfCompanyProducts(company.getId(), company.getAmountOfProducts() + 1);
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
        Product product = getProductById(id);
        Company company = companyRepository.getCompanyById(product.getCompanyId());
        companyRepository.updateAmountOfCompanyProducts(company.getId(), company.getAmountOfProducts() - 1);

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
            statement.setFloat(3, product.getAverageCost());
            statement.setFloat(4, product.getAverageSellingPrice());
            statement.setInt(5, product.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float calcAverageCostByProductId(int product_id) {
        List<Production> productions = productionRepository.getProductionsByProductId(product_id);
        float sum = 0;
        int amount = 0;
        for(int i = 0; i<productions.size();i++){
            sum+=productions.get(i).getTotalCosts();
            amount+=productions.get(i).getAmount();
        }
        if(amount == 0){
            return 0;
        }
        return sum/amount;
    }

    public float calcAverageSellingPriceByProductId(int product_id){
        List<Sale> sales = saleRepository.getSalesByProductId(product_id);
        float sum = 0;
        int amount = 0;
        for(int i = 0; i<sales.size();i++){
            sum+=sales.get(i).getTotalPrice();
            amount+=sales.get(i).getAmount();
        }
        if(amount == 0){
            return 0;
        }
        return sum/amount;
    }
}
