package com.example.pricemanager.service;

import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.UserRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.io.Charsets;

public class UserService {
    public static Status loginUser(User user) {
        User userFromDb = UserRepository.getUserByLogin(user.getLogin());

        if (userFromDb == null || !getHashOfPassword(user.getPassword()).equals(userFromDb.getPassword())) {
            return Status.INVALID_PASSWORD;
        }
        if(userFromDb.getUserStatus().equals(User.UserStatus.BANNED)) {
            return Status.USER_BANNED;
        }
        return Status.SUCCESS;
    }

    public static String getHashOfPassword(String password) {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        return sha256.toString();
    }

    public static User getUserInfoByInfo(String login) {
        User userFromDb = UserRepository.getUserByLogin(login);
        userFromDb.setPassword("");
        return userFromDb;
    }
}
