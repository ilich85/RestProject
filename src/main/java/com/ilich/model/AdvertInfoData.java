package com.ilich.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilich.util.View;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@EqualsAndHashCode
public class AdvertInfoData {

    private long idAdvertInfo;

    @NotNull
    @Size(min = 3, max = 25)
    @JsonView(View.AdvertDetails.class)
    private String company = "";

    @NotNull
    @Size(min = 1, max = 25)
    @JsonView(View.AdvertDetails.class)
    private String model = "";

    @NotNull
    @Size(min = 3, max = 25)
    @JsonView(View.AdvertDetails.class)
    private String color = "";

    @NotNull
    @Min(1930)
    @Max(2019)
    @JsonView(View.AdvertDetails.class)
    private int yearOfIssue;

    @NotNull
    @Min(100)
    @Max(999999)
    @JsonView(View.AdvertDetails.class)
    private int price;
}
