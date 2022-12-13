package com.example.pricemanager.controller;

import com.example.pricemanager.connection.Client;
import com.example.pricemanager.dto.ChangePasswordDto;
import com.example.pricemanager.dto.UserDto;
import com.example.pricemanager.entity.*;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.service.*;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Client client;
    private final UserService userService;
    private final CompanyService companyService;
    private final ProductService productService;
    private final ProductionService productionService;
    private final SaleService saleService;
    private final PriceCalculationService priceCalculationService;
    private final CostCalculationService costCalculationService;

    public ClientHandler(Socket clientSocket) {
        client = new Client(clientSocket);
        userService = new UserService();
        companyService = new CompanyService();
        productService = new ProductService();
        productionService = new ProductionService();
        saleService = new SaleService();
        priceCalculationService = new PriceCalculationService();
        costCalculationService = new CostCalculationService();
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            Action action = (Action) client.readObject();
            switch (action) {
                case LOGIN: {
                    client.writeObject(userService.loginUser((User) client.readObject()));
                    break;
                }
                case GET_USER_INFO: {
                    client.writeObject(userService.getUserInfoByLogin((String) client.readObject()));
                    break;
                }
                case REGISTRATION: {
                    client.writeObject(userService.registerNewUser((User) client.readObject()));
                    break;
                }
                case EXIT: {
                    client.disConnect();
                    flag = false;
                    break;
                }
                case ADD_NEW_COMPANY: {
                    client.writeObject(companyService.createNewCompany((Company) client.readObject()));
                    break;
                }
                case GET_ALL_USER_COMPANIES: {
                    client.writeObject(companyService.getAllUserCompanies((Integer) client.readObject()));
                    break;
                }
                case DELETE_COMPANY: {
                    companyService.deleteCompanyById((Integer) client.readObject());
                    break;
                }
                case UPDATE_COMPANY: {
                    client.writeObject(companyService.updateCompany((Company) client.readObject()));
                    break;
                }
                case ADD_NEW_PRODUCT: {
                    productService.createNewProduct((Product) client.readObject());
                    break;
                }
                case DELETE_PRODUCT: {
                    productService.deleteProductById((Integer) client.readObject());
                    break;
                }
                case GET_ALL_COMPANY_PRODUCTS: {
                    client.writeObject(productService.getAllCompanyProducts((Integer) client.readObject()));
                    break;
                }
                case UPDATE_PRODUCT: {
                    productService.updateProduct((Product) client.readObject());
                    break;
                }
                case ADD_NEW_PRODUCTION: {
                    productionService.createNewProduction((Production) client.readObject());
                    break;
                }
                case DELETE_PRODUCTION: {
                    client.writeObject(productionService.deleteProductionById((Integer) client.readObject()));
                    break;
                }
                case GET_ALL_PRODUCT_PRODUCTIONS: {
                    client.writeObject(productionService.getAllProductProductions((Integer) client.readObject()));
                    break;
                }
                case UPDATE_PRODUCTION: {
                    client.writeObject(productionService.updateProduction((Production) client.readObject()));
                    break;
                }
                case ADD_NEW_SALE: {
                    client.writeObject(saleService.createNewSale((Sale) client.readObject()));
                    break;
                }
                case DELETE_SALE: {
                    saleService.deleteSaleById((Integer) client.readObject());
                    break;
                }
                case GET_ALL_PRODUCT_SALES: {
                    client.writeObject(saleService.getAllProductSales((Integer) client.readObject()));
                    break;
                }
                case UPDATE_SALE: {
                    client.writeObject(saleService.updateSale((Sale) client.readObject()));
                    break;
                }
                case GET_ALL_USER_PRICE_CALCULATIONS: {
                    client.writeObject(priceCalculationService.getAllUserPriceCalculations((Integer) client.readObject()));
                    break;
                }
                case ADD_NEW_PRICE_CALCULATION: {
                    client.writeObject(priceCalculationService.createNewCalculation((PriceCalculation) client.readObject()));
                    break;
                }
                case DELETE_ALL_USER_PRICE_CALCULATIONS: {
                    priceCalculationService.deleteAllUserPriceCalculations((Integer) client.readObject());
                    break;
                }
                case GET_ALL_USER_COST_CALCULATIONS: {
                    client.writeObject(costCalculationService.getAllUserCostCalculations((Integer) client.readObject()));
                    break;
                }
                case ADD_NEW_COST_CALCULATION: {
                    client.writeObject(costCalculationService.createNewCalculation((CostCalculation) client.readObject()));
                    break;
                }
                case DELETE_ALL_USER_COST_CALCULATIONS: {
                    costCalculationService.deleteAllUserCostCalculations((Integer) client.readObject());
                    break;
                }
                case CHANGE_PASSWORD:{
                    client.writeObject(userService.changeUserPassword((ChangePasswordDto)client.readObject()));
                    break;
                }
                case GET_ALL_USERS:{
                    client.writeObject(userService.getAllUserDtos());
                    break;
                }
                case UPDATE_USER:{
                    client.writeObject(userService.updateUser((UserDto) client.readObject()));
                    break;
                }
                case GET_DATA_FOR_PRICE_CHART:{
                    client.writeObject(saleService.getInfoForPriceChart((Integer) client.readObject()));
                    break;
                }
                case GET_DATA_FOR_COST_CHART:{
                    client.writeObject(productionService.getInfoForCostChart((Integer) client.readObject()));
                    break;
                }
            }
        }
    }
}
