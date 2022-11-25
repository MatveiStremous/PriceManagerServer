package com.example.pricemanager.controller;

import com.example.pricemanager.connection.Client;
import com.example.pricemanager.entity.*;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.*;

import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Client client;
    private static UserRepository userRepository = new UserRepository();
    private static CompanyRepository companyRepository = new CompanyRepository();
    private static ProductRepository productRepository = new ProductRepository();
    private static ProductionRepository productionRepository = new ProductionRepository();
    private static SaleRepository saleRepository = new SaleRepository();

    public ClientHandler(Socket clientSocket) {
        client = new Client(clientSocket);
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag) {
            Action action = (Action) client.readObject();
            switch (action) {
                case LOGIN: {
                    int id = userRepository.loginUser((User) client.readObject());
                    client.writeObject(id > 0 ? Status.SUCCESS : Status.INVALID_PASSWORD);
                    if (id > 0) {
                        client.writeObject(id);
                    }
                    break;
                }
                case CHECK_ROLE: {
                    client.writeObject(userRepository.getUserRoleByLogin((String) client.readObject()));
                    break;
                }
                case REGISTRATION: {
                    client.writeObject(userRepository.addNewUser((User) client.readObject()) ? Status.SUCCESS : Status.LOGIN_ALREADY_EXISTS);
                    break;
                }
                case EXIT: {
                    client.disConnect();
                    flag = false;
                    break;
                }
                case ADD_NEW_COMPANY: {
                    client.writeObject(companyRepository.addNewCompany((Company) client.readObject()) ? Status.SUCCESS : Status.COMPANY_ALREADY_EXISTS);
                    break;
                }
                case GET_ALL_USER_COMPANIES: {
                    client.writeObject(companyRepository.getCompaniesByUserId((Integer) client.readObject()));
                    break;
                }
                case DELETE_COMPANY: {
                    companyRepository.deleteCompanyById((Integer) client.readObject());
                    break;
                }
                case UPDATE_COMPANY: {
                    client.writeObject(companyRepository.updateCompany((Company) client.readObject()) ? Status.SUCCESS : Status.COMPANY_ALREADY_EXISTS);
                    break;
                }
                case ADD_NEW_PRODUCT: {
                    productRepository.addNewProduct((Product) client.readObject());
                    break;
                }
                case DELETE_PRODUCT: {
                    productRepository.deleteProductById((Integer) client.readObject());
                    break;
                }
                case GET_ALL_COMPANY_PRODUCTS: {
                    client.writeObject(productRepository.getProductsByCompanyId((Integer) client.readObject()));
                    break;
                }
                case UPDATE_PRODUCT: {
                    productRepository.updateProduct((Product) client.readObject());
                    break;
                }
                case ADD_NEW_PRODUCTION: {
                    productionRepository.addNewProduction((Production) client.readObject());
                    break;
                }
                case DELETE_PRODUCTION: {
                    client.writeObject(productionRepository.deleteProductionById((Integer) client.readObject()));
                    break;
                }
                case GET_ALL_PRODUCT_PRODUCTIONS: {
                    client.writeObject(productionRepository.getProductionsByProductId((Integer) client.readObject()));
                    break;
                }
                case UPDATE_PRODUCTION: {
                    client.writeObject(productionRepository.updateProduction((Production) client.readObject()));
                    break;
                }
                case ADD_NEW_SALE: {
                    client.writeObject(saleRepository.addNewSale((Sale) client.readObject()));
                    break;
                }
                case DELETE_SALE: {
                    saleRepository.deleteSaleById((Integer) client.readObject());
                    break;
                }
                case GET_ALL_PRODUCT_SALES: {
                    client.writeObject(saleRepository.getSalesByProductId((Integer) client.readObject()));
                    break;
                }
                case UPDATE_SALE: {
                    client.writeObject(saleRepository.updateSale((Sale) client.readObject()));
                    break;
                }
            }
        }
    }
}
