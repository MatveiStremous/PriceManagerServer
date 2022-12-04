package com.example.pricemanager.service;

import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.UserRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.io.Charsets;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public Status loginUser(User user) {
        User userFromDb = userRepository.getUserByLogin(user.getLogin());

        if (userFromDb == null || !getHashOfPassword(user.getPassword()).equals(userFromDb.getPassword())) {
            return Status.INVALID_PASSWORD;
        }
        if (userFromDb.getUserStatus().equals(User.UserStatus.BANNED)) {
            return Status.USER_BANNED;
        }
        return Status.SUCCESS;
    }

    public User getUserInfoByInfo(String login) {
        User userFromDb = userRepository.getUserByLogin(login);
        userFromDb.setPassword("");
        return userFromDb;
    }

    public Status registerNewUser(User newUser) {
        newUser.setPassword(getHashOfPassword(newUser.getPassword()));
        if (userRepository.addNewUser(newUser)) {
            return Status.SUCCESS;
        } else {
            return Status.LOGIN_ALREADY_EXISTS;
        }
    }

    private String getHashOfPassword(String password) {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        return sha256.toString();
    }

}
