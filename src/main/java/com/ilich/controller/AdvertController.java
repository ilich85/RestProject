package com.ilich.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilich.exception.AccessDeniedException;
import com.ilich.filter.AuthenticationData;
import com.ilich.model.AdvertData;
import com.ilich.model.AdvertInfoData;
import com.ilich.model.FullAdvertInfo;
import com.ilich.service.AdvertInfoService;
import com.ilich.service.AdvertService;
import com.ilich.model.ResultInfo;
import com.ilich.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.ilich.util.PropertyStrings.*;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;


@RestController
@RequestMapping("/adverts")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @Autowired
    private AdvertInfoService advertInfoService;

    @JsonView(View.FullAdvertDetails.class)
    @GetMapping
    public List<FullAdvertInfo> getAll() {
        return advertService.getAll();
    }

    @JsonView(View.FullAdvertDetails.class)
    @GetMapping(value = "/{id}")
    public FullAdvertInfo advertById(@PathVariable long id) {
        return advertService.getFullAdvertById(id);
    }

    @JsonView(View.AdvertDetails.class)
    @GetMapping(value = "/current")
    public List<AdvertInfoData> userAdverts() {
        return advertService.getUserAdverts(parseInt(AuthenticationData.getUserId()));
    }

    @PostMapping
    public ResultInfo addAdvert(@RequestBody @Valid AdvertInfoData advertInfo) {
        AdvertData advert = new AdvertData();
        advert.setAdvertInfoId(advertInfoService.addAdvertInfo(advertInfo));
        advert.setUserId(parseInt(AuthenticationData.getUserId()));
        advertService.addAdvert(advert);
        return new ResultInfo(ADVERT_ADDED);
    }

    @PutMapping(value = "/{id}")
    public ResultInfo updateAdvert(@RequestBody @Valid AdvertInfoData advertInfo, @PathVariable long id) {
        AdvertData advert = advertService.getAdvertById(id);
        if (AuthenticationData.getUserId().equals(valueOf(advert.getUserId()))) {
            advertInfo.setIdAdvertInfo(advert.getAdvertInfoId());
            advertInfoService.updateAdvertInfo(advertInfo);
            return new ResultInfo(ADVERT_UPDATED);
        }
        throw new AccessDeniedException(ACCESS_DENIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResultInfo removeAdvert(@PathVariable long id) {
        AdvertData advert = advertService.getAdvertById(id);
        if (advert.getUserId() != parseInt(AuthenticationData.getUserId())) {
            throw new AccessDeniedException(ACCESS_DENIED);
        }
        advertService.removeAdvert(advert.getAdvertInfoId());
        return new ResultInfo(ADVERT_REMOVED);
    }
}
