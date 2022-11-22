package com.example.pricemanager.controller;

import com.example.pricemanager.connection.Client;
import com.example.pricemanager.entity.Company;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.CompanyRepository;
import com.example.pricemanager.repo.UserRepository;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Client client;
    private static UserRepository userRepository = new UserRepository();
    private static CompanyRepository companyRepository = new CompanyRepository();
    public ClientHandler(Socket clientSocket) {
        client = new Client(clientSocket);
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            Action action = (Action) client.readObject();
            switch (action){
                case LOGIN:{
                    int id = userRepository.loginUser((User)client.readObject());
                    client.writeObject(id>0 ? Status.SUCCESS : Status.INVALID_PASSWORD);
                    if(id>0){
                        client.writeObject(id);
                    }
                    break;
                }
                case CHECK_ROLE:{
                    client.writeObject(userRepository.getUserRoleByLogin((String)client.readObject()));
                    break;
                }
                case REGISTRATION:{
                    client.writeObject(userRepository.addNewUser((User)client.readObject()) ? Status.SUCCESS : Status.LOGIN_ALREADY_EXISTS);
                    break;
                }
                case EXIT:{
                    client.disConnect();
                    flag = false;
                    break;
                }
                case ADD_NEW_COMPANY:{
                    client.writeObject(companyRepository.addNewCompany((Company)client.readObject()) ? Status.SUCCESS : Status.COMPANY_ALREADY_EXISTS);
                    break;
                }
                case GET_ALL_USER_COMPANIES:{
                    client.writeObject(companyRepository.getCompaniesByUserId((Integer)client.readObject()));
                    break;
                }
                case DELETE_COMPANY:{
                    companyRepository.deleteCompanyById((Integer) client.readObject());
                    break;
                }
                case UPDATE_COMPANY:{
                    client.writeObject(companyRepository.updateCompany((Company) client.readObject()) ? Status.SUCCESS : Status.COMPANY_ALREADY_EXISTS);
                    break;
                }
            }
        }
    }
}
