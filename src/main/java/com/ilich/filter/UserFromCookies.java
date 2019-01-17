package com.ilich.filter;

import javax.servlet.http.Cookie;

import static com.ilich.util.PropertyStrings.CURRENT_USER;


class UserFromCookies {

    static String getUserId(Cookie[] cookies) {
        String idUser = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(CURRENT_USER)) {
                    idUser = cookie.getValue();
                }
            }
        }
        return idUser;
    }
}
