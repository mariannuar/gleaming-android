package com.example.gleaming.network;

import androidx.annotation.Nullable;

import com.example.gleaming.model.AccessToken;
import com.example.gleaming.utils.TokenManager;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class CustomAuthenticator implements Authenticator {

    private TokenManager tokenManager;
    private static CustomAuthenticator INSTANCE;

    private CustomAuthenticator(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    static synchronized CustomAuthenticator getInstance(TokenManager tokenManager) {
        if (INSTANCE == null) INSTANCE = new CustomAuthenticator(tokenManager);
        return INSTANCE;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {

        if (responseCount(response) >= 2) {
            return null;
        }

        AccessToken token = tokenManager.getToken();

        GleamingService service = RetrofitBuilder.getRetrofit().create(GleamingService.class);
        Call<AccessToken> call = service.refreshToken(token.getRefreshToken());

        retrofit2.Response<AccessToken> res = call.execute();

        if (res.isSuccessful()) {

            AccessToken newAccessToken = res.body();
            tokenManager.setToken(newAccessToken);

            return response.request().newBuilder()
                    .header("Authorization", String.format(Locale.ROOT, "%s %s", tokenManager.getToken().getTokenType(), tokenManager.getToken().getAccessToken()))
                    .build();
        }

        return null;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}