package com.example.gleaming.utils;

import android.content.Context;

import com.example.gleaming.model.AccessToken;

public class TokenManager {

    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private static final String TOKEN_TYPE = "TOKEN_TYPE";

    private static TokenManager INSTANCE = null;

    private Context context;

    public TokenManager(Context context) {
        this.context = context;
    }

    public static synchronized TokenManager getInstance(Context context) {
        if (INSTANCE == null) INSTANCE = new TokenManager(context);
        return INSTANCE;
    }

    public void setToken(AccessToken accessToken) {
        AppPreferences.getInstance(context).put(ACCESS_TOKEN, accessToken.getAccessToken());
        AppPreferences.getInstance(context).put(REFRESH_TOKEN, accessToken.getRefreshToken());
        AppPreferences.getInstance(context).put(TOKEN_TYPE, accessToken.getTokenType());
    }

    public void deleteToken() {
        AppPreferences.getInstance(context).remove(ACCESS_TOKEN);
        AppPreferences.getInstance(context).remove(REFRESH_TOKEN);
        AppPreferences.getInstance(context).remove(TOKEN_TYPE);
    }

    public AccessToken getToken() {
        AccessToken accessToken = new AccessToken();

        accessToken.setAccessToken(AppPreferences.getInstance(context).getString(ACCESS_TOKEN, null));
        accessToken.setRefreshToken(AppPreferences.getInstance(context).getString(REFRESH_TOKEN, null));
        accessToken.setTokenType(AppPreferences.getInstance(context).getString(TOKEN_TYPE, null));

        return accessToken;
    }
}
