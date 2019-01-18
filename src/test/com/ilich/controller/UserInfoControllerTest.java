package com.ilich.controller;

import com.ilich.exception.WrongPasswordException;
import com.ilich.filter.AuthenticationData;
import com.ilich.model.ResultInfo;
import com.ilich.model.UserInfoData;
import com.ilich.service.UserAuthDataService;
import com.ilich.service.UserInfoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.ilich.util.PropertyStrings.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserInfoControllerTest {

    private static final String NEW_PASS = "87654321";
    private static final String OLD_PASS = "12345678";

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private UserAuthDataService userAuthDataService;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserInfoController userInfoController;

    private UserInfoData userInfo;
    private Map<String, String> map;
    private Validator validator;

    @Before
    public void setUp() {
        userInfo = new UserInfoData();
        userInfo.setName("Homer");
        userInfo.setCity("Springfield");
        userInfo.setPhone(555123123);

        map = new TreeMap<>();
        map.put(OLD_PASSWORD, OLD_PASS);

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
        AuthenticationData.setUserId("1");
    }

    @Test
    public void userInfoById_whenAllOk_returnUserInfo() {
        when(userInfoService.getUserInfo(1)).thenReturn(userInfo);
        assertEquals(userInfo, userInfoController.userInfoById());
    }

    @Test
    public void addUserInfo_whenDataNotValid_returnException() {
        userInfo.setName("");
        Set<ConstraintViolation<UserInfoData>> violations = validator.validate(userInfo);
        assertFalse(violations.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void addUserInfo_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userInfoService).addUserInfo(userInfo);
        userInfoController.addUserInfo(userInfo);
    }

    @Test
    public void addUserInfo_whenAllOk_returnResultInfo() {
        assertEquals(new ResultInfo(USER_INFO_ADDED),
                userInfoController.addUserInfo(userInfo));
    }

    @Test
    public void updateUserInfo_whenDataNotValid_returnResultInfo() {
        userInfo.setName("");
        Set<ConstraintViolation<UserInfoData>> violations = validator.validate(userInfo);
        assertFalse(violations.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void updateUserInfo_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userInfoService).updateUserInfo(userInfo);
        userInfoController.updateUserInfo(userInfo);
    }

    @Test
    public void updateUserInfo_whenAllOk_returnResultInfo() {
        assertEquals(new ResultInfo(USER_INFO_UPDATED), userInfoController.updateUserInfo(userInfo));
    }

    @Test
    public void updateUserPass_whenPasswordsAreMismatch_returnResultInfo() {
        map.put(NEW_PASSWORD, NEW_PASS);
        map.put(REPEAT_PASSWORD, NEW_PASS + "1");
        assertEquals(new ResultInfo(MISMATCH_PASSWORDS), userInfoController.updateUserPass(map));
    }

    @Test
    public void updateUserPass_whenPasswordTooShort_returnResultInfo() {
        map.put(NEW_PASSWORD, NEW_PASS.substring(0, NEW_PASS.length() - 1));
        map.put(REPEAT_PASSWORD, NEW_PASS.substring(0, NEW_PASS.length() - 1));
        assertEquals(new ResultInfo(SHORT_PASSWORD), userInfoController.updateUserPass(map));
    }

    @Test(expected = WrongPasswordException.class)
    public void updateUserPass_whenOldPasswordIsWrong_returnException() {
        map.put(NEW_PASSWORD, NEW_PASSWORD);
        map.put(REPEAT_PASSWORD, NEW_PASSWORD);
        doThrow(new WrongPasswordException(WRONG_PASSWORD)).when(userAuthDataService)
                .updatePassword(OLD_PASS, NEW_PASSWORD, 1);
        userInfoController.updateUserPass(map);
    }

    @Test(expected = DataAccessException.class)
    public void updateUserPass_whenServerThrowException_returnException() {
        map.put(NEW_PASSWORD, NEW_PASS);
        map.put(REPEAT_PASSWORD, NEW_PASS);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userAuthDataService)
                .updatePassword(OLD_PASS, NEW_PASS, 1);
        userInfoController.updateUserPass(map);
    }

    @Test
    public void updateUserPass_whenAllOk_returnResultInfo() {
        map.put(NEW_PASSWORD, NEW_PASS);
        map.put(REPEAT_PASSWORD, NEW_PASS);
        doNothing().when(userAuthDataService).updatePassword(OLD_PASS, NEW_PASS, 1);
        assertEquals(new ResultInfo(PASSWORD_UPDATED), userInfoController.updateUserPass(map));
    }

    @Test(expected = DataAccessException.class)
    public void deleteUser_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userInfoService).removeProfile(1);
        userInfoController.deleteUser(response);
    }

    @Test
    public void deleteUser_whenAllOk_returnResultInfo() {
        doNothing().when(userInfoService).removeProfile(1);
        assertEquals(new ResultInfo(PROFILE_REMOVED), userInfoController.deleteUser(response));
    }
}
