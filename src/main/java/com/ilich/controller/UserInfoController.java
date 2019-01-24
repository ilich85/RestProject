package com.ilich.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilich.filter.AuthenticationData;
import com.ilich.model.UserInfoData;
import com.ilich.service.UserAuthDataService;
import com.ilich.service.UserInfoService;
import com.ilich.model.ResultInfo;
import com.ilich.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

import static com.ilich.util.PropertyStrings.*;
import static java.lang.Integer.parseInt;


@RestController
@RequestMapping(value = "/profile")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserAuthDataService userAuthDataService;

    @JsonView(View.AuthorDetails.class)
    @GetMapping()
    public UserInfoData userInfoById() {
        return userInfoService.getUserInfo(parseInt(AuthenticationData.getUserId()));
    }

    @PostMapping
    public ResultInfo addUserInfo(@RequestBody @Valid UserInfoData info) {
        info.setUserId(parseInt(AuthenticationData.getUserId()));
        userInfoService.addUserInfo(info);
        return new ResultInfo(USER_INFO_ADDED);
    }

    @PutMapping
    public ResultInfo updateUserInfo(@RequestBody @Valid UserInfoData info) {
        info.setUserId(parseInt(AuthenticationData.getUserId()));
        userInfoService.updateUserInfo(info);
        return new ResultInfo(USER_INFO_UPDATED);
    }

    @PutMapping(value = "/password")
    public ResultInfo updateUserPass(@RequestBody Map<String, String> content) {
        String result = validatePassword(content);
        if (result.equals(VALID)) {
            userAuthDataService.updatePassword(content.get(OLD_PASSWORD),
                    content.get(NEW_PASSWORD), parseInt(AuthenticationData.getUserId()));
            return new ResultInfo(PASSWORD_UPDATED);
        } else {
            return new ResultInfo(result);
        }
    }

    @DeleteMapping
    public ResultInfo deleteUser(HttpServletResponse response) {
        userInfoService.removeProfile(parseInt(AuthenticationData.getUserId()));
        response.addCookie(new Cookie(CURRENT_USER, ""));
        return new ResultInfo(PROFILE_REMOVED);
    }

    private String validatePassword(Map<String, String> map) {
        if (!map.get(NEW_PASSWORD).equals(map.get(REPEAT_PASSWORD))) {
            return MISMATCH_PASSWORDS;
        }
        if (map.get(NEW_PASSWORD).length() < 8) {
            return SHORT_PASSWORD;
        }
        return VALID;
    }
}
