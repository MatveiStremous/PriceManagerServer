package com.example.pricemanager.service;

import com.example.pricemanager.dto.ChartDto;
import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.Product;
import com.example.pricemanager.entity.Production;
import com.example.pricemanager.entity.Sale;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.CompanyRepository;
import com.example.pricemanager.repo.ProductRepository;
import com.example.pricemanager.repo.ProductionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductionService {
    private final ProductionRepository productionRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public ProductionService() {
        productionRepository = new ProductionRepository();
        productRepository = new ProductRepository();
        companyRepository = new CompanyRepository();
    }

    public void createNewProduction(Production production) {
        productionRepository.addNewProduction(production);

        Product product = productRepository.getProductById(production.getProductId());
        product.setAmount(product.getAmount() + production.getAmount());
        product.setAverageCost(calcAverageCostByProductId(production.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() - production.getTotalCosts());
        companyRepository.updateCompany(company);
    }

    public Status deleteProductionById(Integer production_id) {
        Production production = productionRepository.getProductionById(production_id);
        Product product = productRepository.getProductById(production.getProductId());

        if (product.getAmount() - production.getAmount() < 0) {
            return Status.NOT_ENOUGH_PRODUCTS;
        }

        productionRepository.deleteProductionById(production_id);

        product.setAmount(product.getAmount() - production.getAmount());
        product.setAverageCost(calcAverageCostByProductId(production.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() + production.getTotalCosts());
        companyRepository.updateCompany(company);

        return Status.SUCCESS;
    }

    public Status updateProduction(Production production) {
        Production oldProduction = productionRepository.getProductionById(production.getId());
        Product product = productRepository.getProductById(production.getProductId());

        if (product.getAmount() - oldProduction.getAmount() + production.getAmount() < 0) {
            return Status.NOT_ENOUGH_PRODUCTS;
        }

        productionRepository.updateProduction(production);

        product.setAmount(product.getAmount() - oldProduction.getAmount() + production.getAmount());
        product.setAverageCost(calcAverageCostByProductId(production.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() + oldProduction.getTotalCosts() - production.getTotalCosts());
        companyRepository.updateCompany(company);

        return Status.SUCCESS;
    }

    private float calcAverageCostByProductId(int productId) {
        List<Production> productions = productionRepository.getProductionsByProductId(productId);
        float sum = 0;
        int amount = 0;
        for (int i = 0; i < productions.size(); i++) {
            sum += productions.get(i).getTotalCosts();
            amount += productions.get(i).getAmount();
        }
        if (amount == 0) {
            return 0;
        }
        return Math.round(sum*100 / amount)/100.0f;
    }

    public List<Production> getAllProductProductions(int product_id) {
        return productionRepository.getProductionsByProductId(product_id);
    }

    public List<ChartDto> getInfoForCostChart(int productId) {
        List<ChartDto> data = new ArrayList<>();
        List<Production> productions = productionRepository.getProductionsByProductId(productId);
        Collections.sort(productions);
        int amount = 0;
        double sum = 0;
        for (int i = 0; i < productions.size(); i += 1) {
            ChartDto chartDto = new ChartDto();
            Production production = productions.get(i);
            amount = production.getAmount();
            sum = production.getTotalCosts();
            if (i + 1 < productions.size()) {
                while (i + 1 < productions.size()) {
                    if (productions.get(i + 1).getDate().isEqual(production.getDate())) {
                        amount += productions.get(i + 1).getAmount();
                        sum += productions.get(i + 1).getTotalCosts();
                        i += 1;
                    } else {
                        break;
                    }
                }
            }
            data.add(new ChartDto(Math.round(sum*100/amount)/100.0, production.getDate()));
        }
        return data;
    }
}
