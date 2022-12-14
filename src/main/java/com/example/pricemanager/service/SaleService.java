package com.example.pricemanager.service;

import com.example.pricemanager.dto.ChartDto;
import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.Product;
import com.example.pricemanager.entity.Sale;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.CompanyRepository;
import com.example.pricemanager.repo.ProductRepository;
import com.example.pricemanager.repo.SaleRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    public SaleService() {
        saleRepository = new SaleRepository();
        productRepository = new ProductRepository();
        companyRepository = new CompanyRepository();
    }


    public Status createNewSale(Sale sale) {
        Product product = productRepository.getProductById(sale.getProductId());
        if (product.getAmount() - sale.getAmount() < 0) {
            return Status.NOT_ENOUGH_PRODUCTS;
        }

        saleRepository.addNewSale(sale);

        product.setAmount(product.getAmount() - sale.getAmount());
        product.setAverageSellingPrice(calcAverageSellingPriceByProductId(sale.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() + sale.getTotalPrice());
        companyRepository.updateCompany(company);

        return Status.SUCCESS;
    }

    public void deleteSaleById(int saleId) {
        Sale sale = saleRepository.getSaleById(saleId);

        saleRepository.deleteSaleById(saleId);

        Product product = productRepository.getProductById(sale.getProductId());
        product.setAmount(product.getAmount() + sale.getAmount());
        product.setAverageSellingPrice(calcAverageSellingPriceByProductId(sale.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() - sale.getTotalPrice());
        companyRepository.updateCompany(company);
    }

    public Status updateSale(Sale sale) {
        Sale oldSale = saleRepository.getSaleById(sale.getId());
        Product product = productRepository.getProductById(sale.getProductId());

        if (product.getAmount() + oldSale.getAmount() - sale.getAmount() < 0) {
            return Status.NOT_ENOUGH_PRODUCTS;
        }

        saleRepository.updateSale(sale);

        product.setAmount(product.getAmount() + oldSale.getAmount() - sale.getAmount());
        product.setAverageSellingPrice(calcAverageSellingPriceByProductId(sale.getProductId()));
        productRepository.updateProduct(product);

        Company company = companyRepository.getCompanyById(product.getCompanyId());
        company.setBalance(company.getBalance() - oldSale.getTotalPrice() + sale.getTotalPrice());
        companyRepository.updateCompany(company);
        return Status.SUCCESS;
    }

    private float calcAverageSellingPriceByProductId(int productId) {
        List<Sale> sales = saleRepository.getSalesByProductId(productId);
        float sum = 0;
        int amount = 0;
        for (int i = 0; i < sales.size(); i++) {
            sum += sales.get(i).getTotalPrice();
            amount += sales.get(i).getAmount();
        }
        if (amount == 0) {
            return 0;
        }
        return Math.round(sum * 100 / amount) / 100.0f;
    }

    public List<Sale> getAllProductSales(int productId) {
        return saleRepository.getSalesByProductId(productId);
    }

    public List<ChartDto> getInfoForPriceChart(int productId) {
        List<ChartDto> data = new ArrayList<>();
        List<Sale> sales = saleRepository.getSalesByProductId(productId);
        Collections.sort(sales);
        int amount = 0;
        double sum = 0;
        for (int i = 0; i < sales.size(); i += 1) {
            Sale sale = sales.get(i);
            amount = sale.getAmount();
            sum = sale.getTotalPrice();
            if (i + 1 < sales.size()) {
                while (i + 1 < sales.size()) {
                    if (sales.get(i + 1).getDate().isEqual(sale.getDate())) {
                        amount += sales.get(i + 1).getAmount();
                        sum += sales.get(i + 1).getTotalPrice();
                        i += 1;
                    } else {
                        break;
                    }
                }
            }
            data.add(new ChartDto(Math.round(sum*100/amount)/100.0, sale.getDate()));
        }
        return data;
    }
}
