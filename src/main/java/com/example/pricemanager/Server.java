package com.example.pricemanager;

import com.example.pricemanager.controller.ClientHandler;
import com.example.pricemanager.repo.CompanyRepository;
import com.example.pricemanager.repo.UserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(8000);
        System.out.println("Server is ready");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println(clientSocket);

            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}
