package com.example.gleaming;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.gleaming.model.AccessToken;
import com.example.gleaming.model.User;
import com.example.gleaming.model.UserSingleResponse;
import com.example.gleaming.network.GleamingService;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserProfileActivity extends Fragment implements View.OnClickListener {
    private AppCompatActivity activity;
    private ImageView pictureProfile;
    private TextView userName;
    private TextView userEmail;
    private Retrofit retrofit;
    private TokenManager tokenManager;
    private TextView txtLogout;
    private TextView txtEdit;
    private User user;

    private String TAG = "UserProfileActivity";


    public UserProfileActivity() {

    }

    public static UserProfileActivity newInstance() {
        UserProfileActivity fragment = new UserProfileActivity();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtLogout = view.findViewById(R.id.logout);
        txtLogout.setOnClickListener(this);

        txtEdit = view.findViewById(R.id.edit_details);
        txtEdit.setOnClickListener(this);

        pictureProfile = view.findViewById(R.id.photo_profile);
        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);

        Intent intent = activity.getIntent();
        tokenManager = TokenManager.getInstance(activity);

        if (intent != null) {
            getUser();
        }
    }

    private void getUser() {

        GleamingService service = RetrofitBuilder.createServiceWithAuth(GleamingService.class, tokenManager);

        Call<UserSingleResponse> userResponseCall = service.getUser();

        userResponseCall.enqueue(new Callback<UserSingleResponse>() {
            @Override
            public void onResponse(Call<UserSingleResponse> call, Response<UserSingleResponse> response) {

                Log.i(TAG, "onResponse: " + response.toString());
                if (response.isSuccessful()) {

                    UserSingleResponse singleUserResponse = response.body();

                    user = singleUserResponse.getUser();

                    userName.setText(String.format("%s %s", user.getName(), user.getLastname()));
                    userEmail.setText(user.getEmail());

                    Glide.with(activity)
                            .load("http://gleaming.nickgonzalezs.com/storage/images/" + user.getImg())
                            .into(pictureProfile);

                } else {
                    Log.i(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<UserSingleResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick:" + v.getId());
        switch (v.getId()) {
            case R.id.edit_details:
                //Log.i(TAG, "user " + user.toString());
                edit(user);
                break;


            case R.id.logout:
                logOut();
                break;
        }

    }

    private void logOut() {

        GleamingService service = RetrofitBuilder.createServiceWithAuth(GleamingService.class, tokenManager);

        Call<AccessToken> accessTokenCall = service.logout();

        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                Log.i(TAG, "response " + response.toString());

                if (response.isSuccessful()) {
                    if (tokenManager.getToken().getAccessToken() != null) {
                        tokenManager.deleteToken();
                        startActivity(new Intent(activity, LoginActivity.class));
                        activity.finish();
                    }
                } else {
                    Log.i(TAG, response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void edit(User user) {
        Intent intent = new Intent(activity, EditUserActivity.class);

        Log.i(TAG, "user " + user.getImg());

        intent.putExtra("id", user.getId());
        intent.putExtra("name", user.getName());
        intent.putExtra("lastname", user.getLastname());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("age", user.getAge());
        startActivity(intent);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
