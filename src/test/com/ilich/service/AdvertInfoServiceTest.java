package com.ilich.service;

import com.ilich.model.AdvertInfoData;
import com.ilich.repository.AdvertInfoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import static com.ilich.util.PropertyStrings.ERROR;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AdvertInfoServiceTest {

    @Mock
    private AdvertInfoRepository advertInfoRepository;
    @InjectMocks
    private AdvertInfoService advertInfoService;

    private AdvertInfoData advertInfo;

    @Before
    public void setUp() {
        advertInfo = new AdvertInfoData();
        advertInfo.setCompany("Audi");
        advertInfo.setModel("Q7");
        advertInfo.setColor("Black");
        advertInfo.setYearOfIssue(2014);
        advertInfo.setPrice(17000);
    }

    @Test
    public void addAdvertInfo_whenAllOk_returnId() {
        when(advertInfoRepository.add(advertInfo)).thenReturn(1L);
        assertEquals(1L, advertInfoService.addAdvertInfo(advertInfo));
    }

    @Test(expected = DataAccessException.class)
    public void addAdvertInfo_whenServerThrowException_returnException() {
        when(advertInfoRepository.add(advertInfo)).thenThrow(new DataAccessResourceFailureException("error"));
        advertInfoService.addAdvertInfo(advertInfo);
    }

    @Test
    public void updateAdvertInfo_whenAllOk_withoutReturn() {
        doNothing().when(advertInfoRepository).update(advertInfo);
        advertInfoService.updateAdvertInfo(advertInfo);
    }

    @Test(expected = DataAccessException.class)
    public void updateAdvertInfo_whenServerThrowException_returnException() {
        doThrow(new DataAccessResourceFailureException(ERROR)).when(advertInfoRepository).update(advertInfo);
        advertInfoService.updateAdvertInfo(advertInfo);
    }
}
