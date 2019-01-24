package com.ilich.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class AdvertData {

    private long idAdvert;
    private long advertInfoId;
    private int userId;
    private String placingDate;
}
