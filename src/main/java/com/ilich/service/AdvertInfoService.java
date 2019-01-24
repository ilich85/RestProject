package com.ilich.service;

import com.ilich.model.AdvertInfoData;
import com.ilich.repository.AdvertInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdvertInfoService {

    @Autowired
    private AdvertInfoRepository advertInfoRepository;

    public long addAdvertInfo(AdvertInfoData advertInfo) {
        return advertInfoRepository.add(advertInfo);
    }

    public void updateAdvertInfo(AdvertInfoData advertInfo) {
        advertInfoRepository.update(advertInfo);
    }
}
