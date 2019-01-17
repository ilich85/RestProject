package com.ilich.service;

import com.ilich.model.AdvertData;
import com.ilich.model.AdvertInfoData;
import com.ilich.repository.AdvertInfoRepository;
import com.ilich.repository.AdvertRepository;
import com.ilich.repository.UserInfoRepository;
import com.ilich.model.FullAdvertInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdvertService {

    @Autowired
    private AdvertRepository advertRepository;

    @Autowired
    private AdvertInfoRepository advertInfoRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public List<FullAdvertInfo> getAll() {
        return fullAdvertsInfo(advertRepository.findAll());
    }

    public FullAdvertInfo getFullAdvertById(long id) {
        return getFullAdvertInfo(getAdvertById(id));
    }

    public AdvertData getAdvertById(long id) {
        return advertRepository.findOne(id);
    }

    public List<AdvertInfoData> getUserAdverts(int id) {
        List<AdvertInfoData> adverts = new ArrayList<>();
        getAdvertsIdByUserId(id).forEach(idAdvert -> adverts.add(advertInfoRepository.findOne(idAdvert)));
        return adverts;
    }

    public void addAdvert(AdvertData advert) {
        advertRepository.add(advert);
    }

    public void removeAdvert(long id) {
        advertInfoRepository.remove(advertRepository.findOne(id).getAdvertInfoId());
    }

    private List<FullAdvertInfo> fullAdvertsInfo(List<AdvertData> adverts) {
        List<FullAdvertInfo> ads = new ArrayList<>();
        adverts.forEach(advert -> ads.add(getFullAdvertInfo(advert)));
        return ads;
    }

    private List<Integer> getAdvertsIdByUserId(int id) {
        return advertRepository.usersAdverts(id);
    }

    private FullAdvertInfo getFullAdvertInfo(AdvertData advert) {
        return new FullAdvertInfo(advertInfoRepository.findOne(advert.getAdvertInfoId()),
                userInfoRepository.findOne(advert.getUserId()));
    }
}
