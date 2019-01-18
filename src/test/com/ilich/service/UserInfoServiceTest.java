package com.ilich.service;

import com.ilich.model.UserInfoData;
import com.ilich.repository.AdvertInfoRepository;
import com.ilich.repository.AdvertRepository;
import com.ilich.repository.UserAuthDataRepository;
import com.ilich.repository.UserInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.ArrayList;
import java.util.List;

import static com.ilich.util.PropertyStrings.ERROR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class UserInfoServiceTest {

    private UserInfoData userInfo;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private AdvertInfoRepository advertInfoRepository;

    @Mock
    private UserAuthDataRepository userAuthDataRepository;

    @InjectMocks
    private UserInfoService userInfoService;

    @Before
    public void setUp() {
        userInfo = new UserInfoData();
        userInfo.setName("Pavel");
        userInfo.setCity("Poltava");
        userInfo.setPhone(333333333);
        userInfo.setUserId(3);
    }

    @Test
    public void getUserInfo() {
        when(userInfoRepository.findOne(1)).thenReturn(userInfo);
        assertEquals(userInfo, userInfoService.getUserInfo(1));
    }

    @Test
    public void addUserInfo_whenAllOk_withoutReturn() {
        doNothing().when(userInfoRepository).add(userInfo);
        userInfoService.addUserInfo(userInfo);
    }

    @Test(expected = DataAccessException.class)
    public void addUserInfo_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userInfoRepository).add(userInfo);
        userInfoService.addUserInfo(userInfo);
    }

    @Test
    public void updateUserInfo_whenAllOk_withoutReturn() {
        doNothing().when(userInfoRepository).update(userInfo);
        userInfoService.updateUserInfo(userInfo);
    }

    @Test(expected = DataAccessException.class)
    public void updateUserInfo_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userInfoRepository).update(userInfo);
        userInfoService.updateUserInfo(userInfo);
    }

    @Test
    public void removeProfile_whenAllOk_withoutReturn() {
        List<Integer> adverts = new ArrayList<>();
        adverts.add(1);
        adverts.add(2);

        when(advertRepository.usersAdverts(1)).thenReturn(adverts);
        doNothing().when(advertInfoRepository).remove(1);
        doNothing().when(advertInfoRepository).remove(2);
        doNothing().when(advertRepository).removeUserAdverts(1);
        doNothing().when(userInfoRepository).remove(1);
        doNothing().when(userAuthDataRepository).remove(1);
        userInfoService.removeProfile(1);
    }

    @Test(expected = DataAccessException.class)
    public void removeProfile_whenServerThrowException_returnException() {
        when(advertRepository.usersAdverts(2)).thenReturn(new ArrayList<>());
        doNothing().when(advertInfoRepository).remove(2);
        doNothing().when(advertRepository).removeUserAdverts(2);
        doNothing().when(userInfoRepository).remove(2);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(userAuthDataRepository).remove(2);
        userInfoService.removeProfile(2);
    }
}
