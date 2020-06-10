package com.example.gleaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gleaming.network.GleamingService;
import com.example.gleaming.model.AccessToken;
import com.example.gleaming.model.User;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity<name, email, password> extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RegisterActivity";
    private TextInputEditText edName;
    private TextInputEditText edEmail;
    private TextInputEditText edPassword;
    private TextInputEditText edLastname;
    private EditText edDate;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edName = findViewById(R.id. txt_name);
        edLastname = findViewById(R.id. txt_lastname);
        edDate = findViewById(R.id. txt_date);
        edEmail = findViewById(R.id.txt_email);
        edPassword = findViewById(R.id.txt_password);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        TextView txtGoToLogin = findViewById(R.id.go_to_login);
        txtGoToLogin.setOnClickListener(this);

        tokenManager = TokenManager.getInstance(this);

    }

    @Override
    public void onClick(View v) {

        Log.d(TAG, "onClick:" + v.getId());
        switch (v.getId()){
            case R.id.btn_register:
                makeRegister();
                break;

            case R.id.go_to_login:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void makeRegister() {
        String name = edName.getText().toString();
        String lastname = edLastname.getText().toString();
        String age = edDate.getText().toString();
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if(name.isEmpty()){
            edName.setError("Require");
            return;
        }

        if(email.isEmpty()){
            edEmail.setError("Require");
            return;
        }

        if(password.isEmpty()){
            edPassword.setError("Require");
            return;
        }

        GleamingService service = RetrofitBuilder.getRetrofit().create(GleamingService.class);

        Call<AccessToken> registerResponseCall = service.register(name, lastname, age, email, password);

        registerResponseCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, response.body().toString());
                    tokenManager.setToken(response.body());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    RegisterActivity.this.finish();
                } else{
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
