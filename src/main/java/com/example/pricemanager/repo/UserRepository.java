package com.example.pricemanager.repo;

import com.example.pricemanager.entity.User;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.io.Charsets;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserRepository implements Repository{

    public void addNewUser(User user) {
        String sqlRequest = "INSERT INTO user (login, password, role) " +
                "VALUES(?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlRequest);
            statement.setString(1, user.getLogin());
            statement.setString(2, UserRepository.getHashOfPassword(user.getPassword()));
            statement.setString(3, String.valueOf(user.getUserRole()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean loginUser(User user) {

        try {
            Statement statement = connection.createStatement();
            String sql  = "SELECT * FROM user " +
                    "WHERE login = \'" +user.getLogin()+"\'";
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next()){
                User userFromDb = new User();
                userFromDb.setId(rs.getInt("user_id"));
                userFromDb.setLogin(rs.getString("login"));
                userFromDb.setPassword(rs.getString("password"));
                String role = rs.getString("role");
                if(role.equals("ADMIN_ROLE")){
                    userFromDb.setUserRole(User.UserRole.ADMIN_ROLE);
                }
                else{
                    userFromDb.setUserRole(User.UserRole.USER_ROLE);
                }
                if(UserRepository.getHashOfPassword(user.getPassword()).equals(userFromDb.getPassword())){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static String getHashOfPassword(String password){
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        return sha256.toString();
    }
}