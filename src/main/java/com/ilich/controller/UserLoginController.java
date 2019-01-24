package com.ilich.controller;

import com.ilich.model.ResultInfo;
import com.ilich.model.UserAuthData;
import com.ilich.service.UserAuthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.ilich.util.PropertyStrings.*;
import static java.lang.String.valueOf;

@RestController
@RequestMapping(value = "/signin")
public class UserLoginController {

    @Autowired
    private UserAuthDataService userAuthDataService;

    @GetMapping
    public ResultInfo logInPage() {
        return new ResultInfo(LOGIN_PAGE);
    }

    @PostMapping
    public ResultInfo logIn(@RequestBody @Valid UserAuthData user, HttpServletResponse response) {
        int userId = userAuthDataService.login(user);
        if (userId == 0) {
            return new ResultInfo(INCORRECT_AUTH_DATA);
        }
        response.addCookie(new Cookie(CURRENT_USER, valueOf(userId)));
        return new ResultInfo(MAIN_PAGE);
    }
}
