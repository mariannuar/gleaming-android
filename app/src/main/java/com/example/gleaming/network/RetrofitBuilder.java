package com.example.gleaming.network;

import com.example.gleaming.utils.TokenManager;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBuilder {

    private static final OkHttpClient client = buildClient();
    private static final Retrofit retrofit = buildRetrofit(client);
    private static final String BASE_URL = "http://gleaming.nickgonzalezs.com/api/";

    private static OkHttpClient buildClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest.newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection", "close");

                        originalRequest = builder.build();

                        return chain.proceed(originalRequest);
                    }
                });

        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createService(final Class<T> service) {
        return retrofit.create(service);
    }

    public static <T> T createServiceWithAuth(final Class<T> service, final TokenManager tokenManager) {

        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if (tokenManager.getToken().getAccessToken() != null) {
                    builder
                            .addHeader("Authorization", String.format(Locale.ROOT, "%s %s", tokenManager.getToken().getTokenType(), tokenManager.getToken().getAccessToken()));
                }

                request = builder.build();

                return chain.proceed(request);
            }
        }).authenticator(CustomAuthenticator.getInstance(tokenManager)).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();

        return newRetrofit.create(service);
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }

}
