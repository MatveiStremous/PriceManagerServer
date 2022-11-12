package com.example.pricemanager.controller;

import com.example.pricemanager.action.Action;
import com.example.pricemanager.connection.Client;
import com.example.pricemanager.entity.User;
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
                case REGISTRATION:{
                    userRepository.addNewUser((User)client.readObject());
                    break;
                }
                case LOGIN:{
                    boolean isUserLogin = userRepository.loginUser((User)client.readObject());
                    client.writeObject(isUserLogin);
                    break;
                }
            }
        }
    }
}
