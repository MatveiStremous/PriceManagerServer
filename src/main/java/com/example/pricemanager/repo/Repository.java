package com.example.pricemanager.repo;

import com.example.pricemanager.db.DataBaseConnection;

import java.sql.Connection;

public interface Repository {
    Connection connection = DataBaseConnection.getConnection();
}
