package com.example.pricemanager.repo;

import com.example.pricemanager.dto.UserDto;
import com.example.pricemanager.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserRepository implements Repository {
    public boolean addNewUser(User user) {
        if (getUserByLogin(user.getLogin()) == null) {
            String sqlRequest = "INSERT INTO user (login, password, role, status) " +
                    "VALUES(?, ?, ?, ?)";

            try {
                PreparedStatement statement = connection.prepareStatement(sqlRequest);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getPassword());
                statement.setString(3, String.valueOf(user.getUserRole()));
                statement.setString(4, String.valueOf(user.getUserStatus()));
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        } else {
            return false;
        }
    }

    public User getUserByLogin(String login) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM user " +
                    "WHERE login = '" + login + "'";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                User userFromDb = new User();
                userFromDb.setId(rs.getInt("user_id"));
                userFromDb.setLogin(rs.getString("login"));
                userFromDb.setPassword(rs.getString("password"));
                String role = rs.getString("role");
                if (role.equals("ADMIN_ROLE")) {
                    userFromDb.setUserRole(User.UserRole.ADMIN_ROLE);
                } else {
                    userFromDb.setUserRole(User.UserRole.USER_ROLE);
                }
                String status = rs.getString("status");
                if (status.equals("ACTIVE")) {
                    userFromDb.setUserStatus(User.UserStatus.ACTIVE);
                } else {
                    userFromDb.setUserStatus(User.UserStatus.BANNED);
                }
                return userFromDb;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateUser(User user) {
        String sqlRequest = "UPDATE user SET" +
                " login = ?," +
                " password = ?," +
                " role = ?," +
                " status = ?" +
                " WHERE user_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, String.valueOf(user.getUserRole()));
            statement.setString(4, String.valueOf(user.getUserStatus()));
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserDto> getAllUserDtos() {
        List<UserDto> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sqlResponse = "SELECT * FROM user ";
            ResultSet rs = statement.executeQuery(sqlResponse);
            while (rs.next()) {
                UserDto userFromDb = new UserDto();
                userFromDb.setId(rs.getInt("user_id"));
                userFromDb.setLogin(rs.getString("login"));
                String role = rs.getString("role");
                if (role.equals("ADMIN_ROLE")) {
                    userFromDb.setUserRole(User.UserRole.ADMIN_ROLE);
                } else {
                    userFromDb.setUserRole(User.UserRole.USER_ROLE);
                }
                String status = rs.getString("status");
                if (status.equals("ACTIVE")) {
                    userFromDb.setUserStatus(User.UserStatus.ACTIVE);
                } else {
                    userFromDb.setUserStatus(User.UserStatus.BANNED);
                }

                users.add(userFromDb);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;
    }
}
