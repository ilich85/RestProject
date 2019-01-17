package com.ilich.service;

import com.ilich.model.AdvertData;
import com.ilich.model.AdvertInfoData;
import com.ilich.model.UserInfoData;
import com.ilich.repository.AdvertInfoRepository;
import com.ilich.repository.AdvertRepository;
import com.ilich.repository.UserInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ilich.util.PropertyStrings.ERROR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AdvertServiceTest {

    private AdvertData advert1;
    private AdvertInfoData advertInfo1;
    private UserInfoData userInfo1;
    private List<AdvertData> list = new ArrayList<>();
    private String date = new SimpleDateFormat().format(new Date());

    @Mock
    private AdvertRepository advertRepository;

    @InjectMocks
    private AdvertService advertService;

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private AdvertInfoRepository advertInfoRepository;

    @Test(expected = EmptyResultDataAccessException.class)
    public void getAll_whenAdvertsDBisEmpty_returnException() {
        when(advertRepository.findAll()).thenThrow(new EmptyResultDataAccessException(1));
        advertService.getAll();
    }

    @Before
    public void setUp() {
        advert1 = new AdvertData();
        advert1.setIdAdvert(1);
        advert1.setUserId(1);
        advert1.setAdvertInfoId(1);
        advert1.setPlacingDate(date);

        advertInfo1 = new AdvertInfoData();
        advertInfo1.setIdAdvertInfo(1);
        advertInfo1.setCompany("Ford");
        advertInfo1.setModel("Focus");
        advertInfo1.setColor("Yellow");
        advertInfo1.setPrice(2400);
        advertInfo1.setYearOfIssue(1998);

        userInfo1 = new UserInfoData();
        userInfo1.setName("Anton");
        userInfo1.setCity("Kyiv");
        userInfo1.setPhone(111111111);
        userInfo1.setUserId(1);

        list.add(advert1);
    }


    @Test
    public void getAll_whenDBisNotEmpty_returnList() {
        when(advertRepository.findAll()).thenReturn(list);
        when(advertRepository.findOne(1)).thenReturn(advert1);
        when(advertInfoRepository.findOne(1)).thenReturn(advertInfo1);
        when(userInfoRepository.findOne(1)).thenReturn(userInfo1);
        assertEquals(1, advertService.getAll().size());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getAll_whenDBisEmpty_returnException() {
        when(advertRepository.findAll()).thenThrow(new EmptyResultDataAccessException(1));
        advertService.getAll();
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void getFullAdvertById_wrongId_returnException() {
        when(advertRepository.findOne(1)).thenThrow(new EmptyResultDataAccessException(1));
        advertService.getFullAdvertById(1);
    }

    @Test
    public void getFullAdvertById_correctId_returnFullAdvert() {
        when(advertRepository.findOne(1)).thenReturn(advert1);
        when(advertInfoRepository.findOne(1)).thenReturn(advertInfo1);
        when(userInfoRepository.findOne(1)).thenReturn(userInfo1);

        assertEquals("Ford", advertService.getFullAdvertById(1).getAdvertInfo().getCompany());
    }

    @Test
    public void getUserAdverts() {
        AdvertData advert2 = new AdvertData();
        advert2.setUserId(1);
        advert2.setAdvertInfoId(2);
        advert2.setPlacingDate(date);

        AdvertInfoData advertInfo2 = new AdvertInfoData();
        advertInfo2.setCompany("Nissan");
        advertInfo2.setModel("Primera");
        advertInfo2.setColor("Gray");
        advertInfo2.setYearOfIssue(2002);
        advertInfo2.setPrice(4000);
        list.add(advert2);

        List<Integer> userAdverts = new ArrayList<>();
        userAdverts.add(1);
        userAdverts.add(2);
        when(advertRepository.usersAdverts(1)).thenReturn(userAdverts);
        when(advertInfoRepository.findOne(1)).thenReturn(advertInfo1);
        when(advertInfoRepository.findOne(2)).thenReturn(advertInfo2);
        assertEquals(2, advertService.getUserAdverts(1).size());

    }

    @Test(expected = DataAccessException.class)
    public void addAdvert_whenServerError_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertRepository).add(advert1);
        advertService.addAdvert(advert1);
    }

    @Test
    public void addAdvert_whenAllOk_withoutReturn() {
        doNothing().when(advertRepository).add(advert1);
        advertService.addAdvert(advert1);
    }

    @Test(expected = DataAccessException.class)
    public void removeAdvert_whenServerError_returnException() {
        doNothing().when(advertInfoRepository).remove(1);
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertInfoRepository).remove(1);
        when(advertRepository.findOne(1)).thenReturn(advert1);
        advertService.removeAdvert(1);
    }

    @Test
    public void removeAdvert_whenAllOk_withoutReturn() {
        doNothing().when(advertInfoRepository).remove(1);
        when(advertRepository.findOne(1)).thenReturn(advert1);
        doNothing().when(advertInfoRepository).remove(1);
        advertService.removeAdvert(1);
    }
}
