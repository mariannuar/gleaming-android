package com.example.gleaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gleaming.network.GleamingService;
import com.example.gleaming.model.AccessToken;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private Button btnLogin;
    private EditText txtEmail;
    private EditText txtPassword;

    private Call<AccessToken> call;
    private GleamingService gleamingService;
    private TokenManager tokenManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txt_email);
        txtPassword = findViewById(R.id.txt_password);

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        TextView txtGoToRegister = findViewById(R.id.go_to_register);
        txtGoToRegister.setOnClickListener(this);

        TextView txtGoToForgotPassword = findViewById(R.id.go_to_forgot_password);
        txtGoToForgotPassword.setOnClickListener(this);

        gleamingService = RetrofitBuilder.createService(GleamingService.class);

        tokenManager = TokenManager.getInstance(this);

        if (tokenManager.getToken().getAccessToken() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }


    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick:" + v.getId());

        switch (v.getId()) {
            case R.id.btn_login:
                makeLogin();
                //Toast.makeText(this, "Has hecho login", Toast.LENGTH_LONG).show();
                break;

            case R.id.go_to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.go_to_forgot_password:
                intent = new Intent(this, ForgotPassword.class);
                startActivity(intent);
                break;
        }
    }


    private void makeLogin() {


        String email = txtEmail.getText().toString();
        String pass = txtPassword.getText().toString();

        if (email.isEmpty()) {
            txtEmail.setError("Email is require");
            return;
        }

        if (pass.isEmpty()) {
            txtPassword.setError("Password is require");
            return;
        }

        GleamingService service = RetrofitBuilder.getRetrofit().create(GleamingService.class);

        Call<AccessToken> loginResponseCall = service.login(email, pass);

        final AppCompatActivity appCompatActivity = this;

        loginResponseCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {

                Log.i(TAG, "onResponse: " + response);

                if (response.isSuccessful()) {
                    Log.i(TAG, response.body().toString());
                    tokenManager.setToken(response.body());
                    startActivity(new Intent(appCompatActivity, MainActivity.class));
                    appCompatActivity.finish();
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
}