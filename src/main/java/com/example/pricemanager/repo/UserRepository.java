package com.example.pricemanager.repo;

import com.example.pricemanager.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository implements Repository{

    public void addNewUser(User user) {
        String sqlRequest = "INSERT INTO user (login, password, role) " +
                "VALUES(?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, String.valueOf(user.getUserRole()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
