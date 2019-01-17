package com.ilich.controller;

import com.ilich.model.ResultInfo;
import com.ilich.model.UserAuthData;
import com.ilich.service.UserAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.ilich.util.PropertyStrings.*;


@RestController
@RequestMapping(value = "/signup")
public class UserRegisterController {

    @Autowired
    private UserAuthDataService userAuthDataService;

    @GetMapping
    public ResultInfo registerPage() {
        return new ResultInfo(REGISTER_PAGE);
    }

    @PostMapping
    public ResultInfo register(@RequestBody @Valid UserAuthData userAuthData) {
        userAuthDataService.register(userAuthData);
        return new ResultInfo(USER_ADDED);
    }
}
