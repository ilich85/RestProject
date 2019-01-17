package com.ilich.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilich.util.View;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class FullAdvertInfo {

    @NotNull
    @JsonView(View.FullAdvertDetails.class)
    private AdvertInfoData advertInfo;

    @NotNull
    @JsonView(View.FullAdvertDetails.class)
    private UserInfoData userInfo;
}
