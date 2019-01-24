package com.ilich.service;

import com.ilich.exception.UserExistsException;
import com.ilich.exception.UserNotFoundException;
import com.ilich.exception.WrongPasswordException;
import com.ilich.model.UserAuthData;
import com.ilich.repository.UserAuthDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.ilich.util.PropertyStrings.USER_EXISTS;
import static com.ilich.util.PropertyStrings.WRONG_PASSWORD;


@Service
public class UserAuthDataService {

    @Autowired
    private UserAuthDataRepository userAuthDataRepository;

    public int login(UserAuthData user) {
        UserAuthData data = userAuthDataRepository.checkUser(user.getUsername());
        if (data.getPassword().equals(user.getPassword())) {
            return data.getIdUser();
        } else {
            return 0;
        }
    }

    public void register(UserAuthData user) {
        try {
            userAuthDataRepository.checkUser(user.getUsername());
            throw new UserExistsException(USER_EXISTS);
        } catch (UserNotFoundException e) {
            userAuthDataRepository.add(user);
        }
    }

    public void updatePassword(String oldPassword, String newPassword, int idUser) {
        if (userAuthDataRepository.findPassword(idUser).equals(oldPassword)) {
            userAuthDataRepository.update(newPassword, idUser);
        } else {
            throw new WrongPasswordException(WRONG_PASSWORD);
        }
    }
}
