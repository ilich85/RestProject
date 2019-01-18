package com.ilich.controller;

import com.ilich.exception.AccessDeniedException;
import com.ilich.filter.AuthenticationData;
import com.ilich.model.*;
import com.ilich.service.AdvertInfoService;
import com.ilich.service.AdvertService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.ilich.util.PropertyStrings.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AdvertControllerTest {

    @InjectMocks
    private AdvertController advertController;

    @Mock
    private AdvertService advertService;

    @Mock
    private AdvertInfoService advertInfoService;

    private Validator validator;

    private AdvertData advert;

    private AdvertInfoData advertInfo1;
    private AdvertInfoData advertInfo2;

    private FullAdvertInfo fullAdvertInfo1;
    private FullAdvertInfo fullAdvertInfo2;

    @Before
    public void setUp() {
        advert = new AdvertData();
        advert.setAdvertInfoId(1);
        advert.setUserId(1);
        advertInfo1 = new AdvertInfoData();
        advertInfo1.setCompany("BMW");
        advertInfo1.setModel("X6");
        advertInfo1.setColor("Blue");
        advertInfo1.setYearOfIssue(2015);
        advertInfo1.setPrice(20000);

        UserInfoData userInfo1 = new UserInfoData();
        userInfo1.setName("Grisha");
        userInfo1.setCity("Belgorod");
        userInfo1.setPhone(123123123);
        userInfo1.setUserId(1);

        advertInfo2 = new AdvertInfoData();
        advertInfo2.setCompany("Toyota");
        advertInfo2.setModel("Camry");
        advertInfo2.setColor("White");
        advertInfo2.setYearOfIssue(2013);
        advertInfo2.setPrice(9000);

        UserInfoData userInfo2 = new UserInfoData();
        userInfo2.setName("Leonid");
        userInfo2.setCity("Nikolaev");
        userInfo2.setPhone(34343434);
        userInfo2.setUserId(2);

        fullAdvertInfo1 = new FullAdvertInfo(advertInfo1, userInfo1);
        fullAdvertInfo2 = new FullAdvertInfo(advertInfo2, userInfo2);

        AuthenticationData.setUserId("1");

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getAll_whenDBisEmpty_returnException() {
        when(advertService.getAll()).thenThrow(new EmptyResultDataAccessException(1));
         advertController.getAll();
    }

    @Test
    public void getAll_whenDBisNotEmpty_returnList() {
        List<FullAdvertInfo> list = new ArrayList<>();
        list.add(fullAdvertInfo1);
        list.add(fullAdvertInfo2);
        when(advertService.getAll()).thenReturn(list);
        assertEquals(2, advertController.getAll().size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void advertById_whenWrongId_returnException() {
        when(advertService.getFullAdvertById(1)).thenThrow(new EmptyResultDataAccessException(1));
        advertController.advertById(1);
    }

    @Test
    public void advertById_whenCorrectId_returnAdvert() {
        when(advertService.getFullAdvertById(2)).thenReturn(fullAdvertInfo2);
        assertEquals(fullAdvertInfo2, advertController.advertById(2));
    }

    @Test
    public void userAdverts_whenUserNotHaveAdverts_returnEmptyList() {
        when(advertService.getUserAdverts(1)).thenReturn(new ArrayList<>());
        assertEquals(0, advertController.userAdverts().size());
    }

    @Test
    public void userAdverts_whenUserHaveAdverts_returnList() {
        List<AdvertInfoData> list = new ArrayList<>();
        list.add(advertInfo1);
        list.add(advertInfo2);
        when(advertService.getUserAdverts(1)).thenReturn(list);
        assertEquals(2, advertController.userAdverts().size());
    }

    @Test
    public void addAdvert_whenEmptyFieldsArePresent_returnViolations() {
        advertInfo1.setColor("");
        Set<ConstraintViolation<AdvertInfoData>> violations = validator.validate(advertInfo1);
        assertFalse(violations.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void addAdvert_whenAdvertServiceThrowError_returnException() {
        when(advertInfoService.addAdvertInfo(advertInfo1)).thenReturn(1L);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertService).addAdvert(advert);
        advertController.addAdvert(advertInfo1);
    }

    @Test(expected = DataAccessException.class)
    public void addAdvert_whenAdvertInfoServiceThrowError_returnException() {
        when(advertInfoService.addAdvertInfo(advertInfo1)).thenThrow(new DataAccessResourceFailureException(ERROR));
        advertController.addAdvert(advertInfo1);
    }

    @Test
    public void addAdvert_whenAllOk_returnResultInfo() {
        when(advertInfoService.addAdvertInfo(advertInfo1)).thenReturn(1L);
        assertEquals(new ResultInfo(ADVERT_ADDED), advertController.addAdvert(advertInfo1));
    }

    @Test(expected = AccessDeniedException.class)
    public void updateAdvert_whenAuthorDoesNotMatchWithCurrentUser_returnException() {
        AuthenticationData.setUserId("2");
        when(advertService.getAdvertById(1)).thenReturn(advert);
        when(advertService.getFullAdvertById(1)).thenReturn(fullAdvertInfo1);
        advertController.updateAdvert(advertInfo1, 1);
    }

    @Test
    public void updateAdvert_whenAllOk_returnResultInfo() {
        when(advertService.getAdvertById(1)).thenReturn(advert);
        when(advertService.getFullAdvertById(1)).thenReturn(fullAdvertInfo1);
        assertEquals(new ResultInfo(ADVERT_UPDATED), advertController.updateAdvert(advertInfo1, 1));
    }

    @Test
    public void updateAdvert_whenDataNotValid_returnViolations() {
        advertInfo1.setCompany("");
        Set<ConstraintViolation<AdvertInfoData>> violations = validator.validate(advertInfo1);
        assertFalse(violations.isEmpty());
    }

    @Test(expected = DataAccessException.class)
    public void updateAdvert_whenServerThrowException_returnException() {
        when(advertService.getAdvertById(1)).thenReturn(advert);
        when(advertService.getFullAdvertById(1)).thenReturn(fullAdvertInfo1);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertInfoService).updateAdvertInfo(advertInfo1);
        advertController.updateAdvert(advertInfo1, 1);
    }

    @Test(expected = AccessDeniedException.class)
    public void removeAdvert_whenAuthorDoesNotMatchWithCurrentUser_returnException() {
        advert.setUserId(2);
        when(advertService.getAdvertById(1)).thenReturn(advert);
        advertController.removeAdvert(1);
    }

    @Test
    public void removeAdvert_whenAllOk_returnResultInfo() {
        when(advertService.getAdvertById(1)).thenReturn(advert);
        assertEquals(new ResultInfo(ADVERT_REMOVED), advertController.removeAdvert(1));
    }

    @Test(expected = DataAccessException.class)
    public void removeAdvert_whenServerThrowException_returnException() {
        when(advertService.getAdvertById(1)).thenReturn(advert);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertService).removeAdvert(1);
        advertController.removeAdvert(1);
    }
}
