package com.example.pricemanager.service;

import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.Product;
import com.example.pricemanager.repo.CompanyRepository;
import com.example.pricemanager.repo.ProductRepository;
import com.example.pricemanager.repo.ProductionRepository;
import com.example.pricemanager.repo.SaleRepository;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductionRepository productionRepository;
    private final SaleRepository saleRepository;

    public ProductService() {
        productRepository = new ProductRepository();
        companyRepository = new CompanyRepository();
        productionRepository = new ProductionRepository();
        saleRepository = new SaleRepository();
    }

    public void createNewProduct(Product product) {
        productRepository.addNewProduct(product);
        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setAmountOfProducts(company.getAmountOfProducts() + 1);
        companyRepository.updateCompany(company);
    }

    public void deleteProductById(int productId) {
        Product product = productRepository.getProductById(productId);

        productRepository.deleteProductById(productId);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setAmountOfProducts(company.getAmountOfProducts() - 1);
        companyRepository.updateCompany(company);
    }

    public List<Product> getAllCompanyProducts(int companyId) {
        return productRepository.getProductsByCompanyId(companyId);
    }


    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }
}
