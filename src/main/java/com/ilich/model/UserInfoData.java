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
public class UserInfoData {

    private int idUserInfo;

    @NotNull
    @Size(min = 6, max = 25)
    @JsonView(View.AuthorDetails.class)
    private String name = "";

    @NotNull
    @Size(min = 6, max = 25)
    @JsonView(View.AuthorDetails.class)
    private String city = "";

    @NotNull
    @Min(10)
    @Max(10)
    @JsonView(View.AuthorDetails.class)
    private int phone;

    private int userId;
}
