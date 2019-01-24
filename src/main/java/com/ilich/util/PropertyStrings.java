package com.ilich.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class PropertyStrings {

    private static ResourceBundle bundle = ResourceBundle.getBundle("strings",
            new Locale("ru", "RU"), new UTF8Control());

    public static final String CURRENT_USER = "CurrentUser";
    public static final String NEW_PASSWORD = "NewPassword";
    public static final String OLD_PASSWORD = "OldPassword";
    public static final String REPEAT_PASSWORD = "RepeatPassword";
    public static final String VALID = "Valid";
    public static final String ADVERT_ADDED = bundle.getString("advert-added");
    public static final String ADVERT_REMOVED = bundle.getString("advert-removed");
    public static final String ADVERT_UPDATED = bundle.getString("advert-updated");
    public static final String ACCESS_DENIED = bundle.getString("access-denied");
    public static final String ERROR = bundle.getString("error");
    public static final String INCORRECT_AUTH_DATA = bundle.getString("incorrect-auth-data");
    public static final String INCORRECT_DATA = bundle.getString("incorrect-data");
    public static final String LOGIN_PAGE = bundle.getString("login-page");
    public static final String MAIN_PAGE = bundle.getString("main-page");
    public static final String MISMATCH_PASSWORDS = bundle.getString("mismatch-passwords");
    public static final String PASSWORD_UPDATED = bundle.getString("password-updated");
    public static final String PROFILE_REMOVED = bundle.getString("profile-removed");
    public static final String REGISTER_PAGE = bundle.getString("register-page");
    public static final String SHORT_PASSWORD = bundle.getString("short-password");
    public static final String SHORT_USERNAME = bundle.getString("short-username");
    public static final String USER_ADDED = bundle.getString("user-added");
    public static final String USER_EXISTS = bundle.getString("user-exists");
    public static final String USER_INFO_ADDED = bundle.getString("user-info-added");
    public static final String USER_INFO_UPDATED = bundle.getString("user-info-updated");
    public static final String USER_NOT_FOUND = bundle.getString("user-not-found");
    public static final String WRONG_PASSWORD = bundle.getString("wrong-password");
}
