package com.ilich.controller;

import com.ilich.model.UserAuthData;
import com.ilich.service.UserAuthDataService;
import com.ilich.model.ResultInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static com.ilich.util.PropertyStrings.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserLoginControllerTest {

    @Mock
    private UserAuthDataService userAuthDataService;

    @Mock
    HttpServletResponse response;

    @InjectMocks
    private UserLoginController userLoginController;

    private UserAuthData userAuthData;
    private Validator validator;

    @Before
    public void setUp() {
        userAuthData = new UserAuthData();
        userAuthData.setUsername("user");
        userAuthData.setPassword("password");

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void logInPage() {
        assertEquals(new ResultInfo(LOGIN_PAGE), userLoginController.logInPage());
    }

    @Test
    public void logIn_whenUserNotExists_returnResultInfo() {
        when(userAuthDataService.login(userAuthData)).thenReturn(0);
        assertEquals(new ResultInfo(INCORRECT_AUTH_DATA), userLoginController.logIn(userAuthData, response));
    }

    @Test
    public void logIn_whenWrongPassword_returnResultInfo() {
        when(userAuthDataService.login(userAuthData)).thenReturn(0);
        assertEquals(new ResultInfo(INCORRECT_AUTH_DATA), userLoginController.logIn(userAuthData, response));
    }

    @Test
    public void logIn_whenDataNotValid_returnViolations() {
        userAuthData.setUsername("");
        Set<ConstraintViolation<UserAuthData>> violations = validator.validate(userAuthData);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void logIn_whenAllOk_returnResultInfo() {
        when(userAuthDataService.login(userAuthData)).thenReturn(1);
        assertEquals(new ResultInfo(MAIN_PAGE), userLoginController.logIn(userAuthData, response));
    }
}
