package com.example.pricemanager.service;

import com.example.pricemanager.dto.ChangePasswordDto;
import com.example.pricemanager.dto.UserDto;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.UserRepository;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.io.Charsets;

import java.util.List;

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

    public UserDto getUserInfoByLogin(String login) {
        User userFromDb = userRepository.getUserByLogin(login);

        return new UserDto(userFromDb.getId(), userFromDb.getLogin(), userFromDb.getUserRole(), userFromDb.getUserStatus());
    }

    public Status registerNewUser(User newUser) {
        newUser.setPassword(getHashOfPassword(newUser.getPassword()));
        if (userRepository.addNewUser(newUser)) {
            return Status.SUCCESS;
        } else {
            return Status.LOGIN_ALREADY_EXISTS;
        }
    }

    public Status changeUserPassword(ChangePasswordDto changePasswordDto) {
        User user = userRepository.getUserByLogin(changePasswordDto.getLogin());
        if (!user.getPassword().equals(getHashOfPassword(changePasswordDto.getOldPassword()))) {
            return Status.INVALID_PASSWORD;
        }
        user.setPassword(getHashOfPassword(changePasswordDto.getNewPassword()));
        userRepository.updateUser(user);
        return Status.SUCCESS;
    }

    public List<UserDto> getAllUserDtos() {
        return userRepository.getAllUserDtos();
    }

    public Status updateUser(UserDto user) {
        User userDB = userRepository.getUserByLogin(user.getLogin());
        if (userDB.getUserRole().equals(User.UserRole.ADMIN_ROLE)) {
            if (user.getUserRole().equals(User.UserRole.USER_ROLE)) {
                return Status.UNABLE_TO_DEMOTE_ADMIN;
            }
            if (userDB.getUserStatus().equals(User.UserStatus.ACTIVE) && user.getUserStatus().equals(User.UserStatus.BANNED)) {
                return Status.UNABLE_TO_BAN_ADMIN;
            }
        }
        userDB.setUserRole(user.getUserRole());
        userDB.setUserStatus(user.getUserStatus());
        userRepository.updateUser(userDB);
        return Status.SUCCESS;
    }

    public String getHashOfPassword(String password) {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        HashCode sha256 = hasher.hash();

        return sha256.toString();
    }
}
