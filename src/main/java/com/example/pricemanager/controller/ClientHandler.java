package com.example.pricemanager.controller;

import com.example.pricemanager.connection.Client;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Action;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.UserRepository;

import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Client client;
    private UserRepository userRepository;
    public ClientHandler(Socket clientSocket, UserRepository userRepository) {
        client = new Client(clientSocket);
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        boolean flag = true;
        while (flag){
            Action action = (Action) client.readObject();
            switch (action){
                case LOGIN:{
                    boolean isUserLogin = userRepository.loginUser((User)client.readObject());
                    client.writeObject(isUserLogin ? Status.SUCCESS : Status.INVALID_PASSWORD);
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
            }
        }
    }
}
