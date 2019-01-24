package com.ilich.service;

import com.ilich.model.UserInfoData;
import com.ilich.repository.AdvertInfoRepository;
import com.ilich.repository.AdvertRepository;
import com.ilich.repository.UserAuthDataRepository;
import com.ilich.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private AdvertInfoRepository advertInfoRepository;

    @Autowired
    private UserAuthDataRepository userAuthDataRepository;

    public UserInfoData getUserInfo(int id) {
        return userInfoRepository.findOne(id);
    }

    public void addUserInfo(UserInfoData info) {
        userInfoRepository.add(info);
    }

    public void updateUserInfo(UserInfoData info) {
        userInfoRepository.update(info);
    }

    @Transactional
    public void removeProfile(int idUser) {
        for (Integer id : advertRepository.usersAdverts(idUser)) {
            advertInfoRepository.remove(id);
        }
        advertRepository.removeUserAdverts(idUser);
        userInfoRepository.remove(idUser);
        userAuthDataRepository.remove(idUser);
    }
}
