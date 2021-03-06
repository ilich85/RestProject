package com.ilich.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@EqualsAndHashCode
public class UserAuthData {

    private int idUser;

    @NotNull
    @Size(min = 6, max = 25)
    private String username;

    @NotNull
    @Size(min = 6, max = 25)
    private String password;
}
