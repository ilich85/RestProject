package com.ilich.filter;


public class AuthenticationData {

    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static String getUserId() {
        String currentUser = local.get();
        if (currentUser == null) {
            currentUser = "";
        }
        return currentUser;
    }

    public static void setUserId(String userId) {
        local.set(userId);
    }
}
