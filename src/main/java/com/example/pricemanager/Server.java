package com.example.pricemanager;

import com.example.pricemanager.controller.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(8000);
        System.out.println("Server is ready. Port: " + serverSocket.getLocalPort() + ".");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New connected client. IP: " + clientSocket.getInetAddress() + ". Port: " + clientSocket.getPort()+".");

            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
}
