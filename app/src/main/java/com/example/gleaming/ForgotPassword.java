package com.example.gleaming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {

    Button btnpassword;
    EditText email;
    String emailreset;
    StringRequest stringRequest;
    String URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        btnpassword = findViewById(R.id.btn_reset_password);
        email = findViewById(R.id.txt_email);

        resetPassword();

    }

    private void resetPassword() {

        btnpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                emailreset = email.getText().toString();
                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("SUCCESS")){
                            Toast.makeText(getApplicationContext(), "Email successfully sent, please checkyour mail box.", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ForgotPassword.this, "Please check connection", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map <String, String> params = new HashMap<>();
                        params.put("emailreset", emailreset);

                        return super.getParams();
                    }
                };
            }
        });
    }
}
