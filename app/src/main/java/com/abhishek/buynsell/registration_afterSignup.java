package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonIOException;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class registration_afterSignup extends AppCompatActivity {

    private  String REGISTRATION_URL = "http://192.168.1.106:3002/registeruser";

    Button register_btn;
    TextInputLayout full_name_register_layout,dept_name_register_layout,college_name_register_layout;
    TextInputEditText full_name_register,dept_name_register,college_name_register;
    TextView logout;
    Spinner dropdown_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_after_signup);




        register_btn = findViewById(R.id.register_btn);

        full_name_register_layout = findViewById(R.id.full_name_register_layout);
        dept_name_register_layout = findViewById(R.id.dept_name_register_layout);
        college_name_register_layout = findViewById(R.id.college_name_register_layout);

        full_name_register = findViewById(R.id.full_name_register);
        dept_name_register = findViewById(R.id.dept_name_register);
        college_name_register = findViewById(R.id.college_name_register);
        college_name_register.setKeyListener(null);

        dropdown_gender = findViewById(R.id.dropdown_gender);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = registration_afterSignup.this;
                SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();


                Toast.makeText(context,"Logout Succesfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, login_screen.class);
                startActivity(intent);
                finish();
            }
        });


        getSupportActionBar().hide();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateRegister();
            }
        });
    }

    private void ValidateRegister(){
        boolean isValid = true;
        if(full_name_register.getText().toString().isEmpty()){
            full_name_register_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            full_name_register_layout.setError("");
        }
        if(dept_name_register.getText().toString().isEmpty()){
            dept_name_register_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            dept_name_register_layout.setError("");
        }
        if(college_name_register.getText().toString().isEmpty()){
            college_name_register_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            college_name_register_layout.setError("");
        }

        if(isValid){

            final Context context = registration_afterSignup.this;
            SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication",context.MODE_PRIVATE);
            final String shared_token = sharedPreferences.getString("token", "");

            String gender = dropdown_gender.getSelectedItem().toString();
            Integer gender_value = 0;
            if(gender.equals("Female")){
                gender_value = 1;
            }else if(gender.equals("Others")){
                gender_value = 2;
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("fullname", full_name_register.getText().toString());
            params.put("department", dept_name_register.getText().toString());
            params.put("college", college_name_register.getText().toString());
            params.put("gender", gender_value.toString());

            JSONObject jsonObject = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(registration_afterSignup.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, REGISTRATION_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Integer responseCode = response.getInt("responseCode");
                        if(responseCode == 200){
                            Toast.makeText(registration_afterSignup.this, "Registered Successfull", Toast.LENGTH_SHORT).show();
                            String token = response.getString("token");
                            sharedPreference_registeration(token);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(registration_afterSignup.this, "err", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(registration_afterSignup.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("token", shared_token);
                    return  headers;
                }
            };
            requestQueue.add(jsonObjectRequest);

        }
    }
    private  void sharedPreference_registeration(String token){
        Context context = registration_afterSignup.this;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();

        Intent intent = new Intent(registration_afterSignup.this , home_screen.class);
        startActivity(intent);
        finish();
    }
}
