package com.ilich.service;

import com.ilich.exception.UserExistsException;
import com.ilich.exception.UserNotFoundException;
import com.ilich.exception.WrongPasswordException;
import com.ilich.model.UserAuthData;
import com.ilich.repository.UserAuthDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;

import static com.ilich.util.PropertyStrings.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserAuthDataServiceTest {

    @Mock
    private UserAuthDataRepository authDataRepository;

    @InjectMocks
    private UserAuthDataService userAuthService;

    @Test
    public void login_withCorrectData_returnId() {
        UserAuthData data = new UserAuthData();
        data.setIdUser(1);
        data.setUsername("user1");
        data.setPassword("pass1");
        when(authDataRepository.checkUser("user1")).thenReturn(data);
        assertEquals(1, userAuthService.login(data));
    }

    @Test
    public void login_withWrongPass_returnZero() {
        UserAuthData data = new UserAuthData();
        data.setUsername("user2");
        data.setPassword("Right");
        when(authDataRepository.checkUser("user2")).thenReturn(data);
        data = new UserAuthData();
        data.setUsername("user2");
        data.setPassword("Wrong");
        assertEquals(0, userAuthService.login(data));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void login_withWrongUsername_returnException() {
        UserAuthData data = new UserAuthData();
        data.setIdUser(1);
        data.setUsername("someUser");
        data.setPassword("somePass");
        when(authDataRepository.checkUser("someUser")).thenThrow(new EmptyResultDataAccessException(1));
        userAuthService.login(data);
    }

    @Test
    public void register_whenAllOk_withoutReturn() {
        UserAuthData data = new UserAuthData();
        data.setUsername("userNotExists");
        data.setPassword("password");
        doThrow(new UserNotFoundException(USER_NOT_FOUND)).when(authDataRepository).checkUser("userNotExists");
        userAuthService.register(data);
    }

    @Test(expected = UserExistsException.class)
    public void register_whenUserExists_returnException() {
        UserAuthData data = new UserAuthData();
        data.setUsername("userExists");
        data.setPassword("password");
        doReturn(data).when(authDataRepository).checkUser("userExists");
        userAuthService.register(data);
    }

    @Test(expected = DataAccessException.class)
    public void register_whenServerThrowException_returnException() {
        UserAuthData data = new UserAuthData();
        data.setUsername("serverEx");
        data.setPassword("password");
        doThrow(new EmptyResultDataAccessException(1)).when(authDataRepository).checkUser("serverEx");
        doThrow(new DataAccessResourceFailureException(ERROR)).when(authDataRepository).add(data);
        userAuthService.register(data);
    }

    @Test(expected = WrongPasswordException.class)
    public void updatePassword_whenWrongPassword_returnException() {
        doNothing().when(authDataRepository).update(NEW_PASSWORD, 1);
        doReturn(NEW_PASSWORD).when(authDataRepository).findPassword(1);
        userAuthService.updatePassword(OLD_PASSWORD, NEW_PASSWORD, 1);
    }

    @Test(expected = DataAccessException.class)
    public void updatePassword_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(authDataRepository).update(NEW_PASSWORD, 1);
        doReturn(OLD_PASSWORD).when(authDataRepository).findPassword(1);
        userAuthService.updatePassword(OLD_PASSWORD, NEW_PASSWORD, 1);
    }

    @Test
    public void updatePassword_correctOldPassAndValidNewPass_withoutReturn() {
        doNothing().when(authDataRepository).update(NEW_PASSWORD, 1);
        doReturn(OLD_PASSWORD).when(authDataRepository).findPassword(1);
        userAuthService.updatePassword(OLD_PASSWORD, NEW_PASSWORD, 1);
    }
}
