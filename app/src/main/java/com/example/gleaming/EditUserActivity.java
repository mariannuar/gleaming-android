package com.example.gleaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gleaming.model.User;
import com.example.gleaming.network.GleamingService;
import com.example.gleaming.network.RetrofitBuilder;
import com.example.gleaming.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editName;
    private EditText editLastName;
    private EditText editEmail;
    private int id;
    private Button btnEditar;
    private String TAG = "EditUserActivity";
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        GleamingService service = RetrofitBuilder.getRetrofit().create(GleamingService.class);

        editName = findViewById(R.id.edit_name);
        editLastName = findViewById(R.id.edit_lastname);


        editName.setText(getIntent().getStringExtra("name"));
        editLastName.setText(getIntent().getStringExtra("lastname"));
        id = getIntent().getIntExtra("id", 1);

        btnEditar = findViewById(R.id.btn_editar);
        btnEditar.setOnClickListener(this);

        tokenManager = TokenManager.getInstance(this);


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_editar:
                updateUser();
                //Toast.makeText(this, "Has editado al usuario", Toast.LENGTH_LONG).show();
                break;

          /*  case R.id.btn_product_back:
                Intent intent = new Intent(this, UserProfileActivity.class);
                break;*/

        }
    }

    private void updateUser() {
        String name = editName.getText().toString();
        String lastname = editLastName.getText().toString();

        GleamingService service = RetrofitBuilder.createServiceWithAuth(GleamingService.class, tokenManager);
        Call<User> call = service.updateUser(name, lastname);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                Log.i(TAG, response.toString());
                if (response.isSuccessful()) {
                    Intent intent = new Intent(EditUserActivity.this, UserProfileActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                throw new RuntimeException(t);
            }
        });
    }

}
