package com.ilich.controller;

import com.ilich.exception.UserExistsException;
import com.ilich.model.ResultInfo;
import com.ilich.model.UserAuthData;
import com.ilich.service.UserAuthDataService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.ilich.util.PropertyStrings.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class UserRegisterControllerTest {

    @Mock
    private UserAuthDataService userAuthDataService;

    @InjectMocks
    private UserRegisterController userRegisterController;

    private Validator validator;
    private UserAuthData userAuthData;

    @Before
    public void setUp() {
        userAuthData = new UserAuthData();
        userAuthData.setUsername("user1234");
        userAuthData.setPassword("password");

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void registerPage() {
        assertEquals(new ResultInfo(REGISTER_PAGE), userRegisterController.registerPage());
    }

    @Test
    public void register_whenUsernameNotValid_returnResultInfo() {
        userAuthData.setUsername("user");
        Set<ConstraintViolation<UserAuthData>> violations = validator.validate(userAuthData);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void register_whenPasswordNotValid_returnResultInfo() {
        userAuthData.setPassword("pass");
        Set<ConstraintViolation<UserAuthData>> violations = validator.validate(userAuthData);
        assertFalse(violations.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void register_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userAuthDataService).register(userAuthData);
        userRegisterController.register(userAuthData);
    }

    @Test(expected = UserExistsException.class)
    public void register_whenUserExists_returnException() {
        doThrow(new UserExistsException(USER_EXISTS)).when(userAuthDataService).register(userAuthData);
        userRegisterController.register(userAuthData);
    }

    @Test
    public void register_whenAllOk_withoutReturn() {
        doNothing().when(userAuthDataService).register(userAuthData);
        userRegisterController.register(userAuthData);
    }
}
