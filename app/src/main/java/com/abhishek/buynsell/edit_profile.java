package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

import org.json.JSONObject;

import java.util.HashMap;

public class edit_profile extends AppCompatActivity {

    private  String EDIT_PROFILE_URL = "http://192.168.1.106:3002/edituser";

    Button save_btn;
    TextInputLayout full_name_edit_layout,dept_name_edit_layout,college_name_edit_layout;
    TextInputEditText full_name_edit,dept_name_edit,college_name_edit;
    Spinner dropdown_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        save_btn = findViewById(R.id.save_btn);

        full_name_edit_layout = findViewById(R.id.full_name_edit_layout);
        dept_name_edit_layout = findViewById(R.id.dept_name_edit_layout);
        college_name_edit_layout = findViewById(R.id.college_name_edit_layout);

        dropdown_gender = findViewById(R.id.dropdown_gender);
        full_name_edit = findViewById(R.id.full_name_edit);
        dept_name_edit = findViewById(R.id.dept_name_edit);
        college_name_edit = findViewById(R.id.college_name_edit);
        college_name_edit.setKeyListener(null);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validateedit();
            }
        });

    }

    private void Validateedit(){
        boolean isValid = true;
        if(full_name_edit.getText().toString().isEmpty()){
            full_name_edit_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            full_name_edit_layout.setError("");
        }
        if(dept_name_edit.getText().toString().isEmpty()){
            dept_name_edit_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            dept_name_edit_layout.setError("");
        }
        if(college_name_edit.getText().toString().isEmpty()){
            college_name_edit_layout.setError("Shouldn't be Empty");
            isValid = false;
        }else {
            college_name_edit_layout.setError("");
        }
        if(isValid){

            Context context = edit_profile.this;
            SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
            final String shared_token = sharedPreferences.getString("token", "");

            String gender = dropdown_gender.getSelectedItem().toString();
            Integer gender_value = 0;
            if(gender.equals("Female")){
                gender_value = 1;
            }else if(gender.equals("Others")){
                gender_value = 2;
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("fullname", full_name_edit.getText().toString());
            params.put("department", dept_name_edit.getText().toString());
            params.put("college", college_name_edit.getText().toString());
            params.put("gender", gender_value.toString());

            JSONObject jsonObject = new JSONObject(params);
            Log.d("params", params.toString());

            RequestQueue requestQueue = Volley.newRequestQueue(edit_profile.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, EDIT_PROFILE_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        Integer responseCode = response.getInt("responseCode");
                        if(responseCode == 200){
                            Toast.makeText(edit_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                            String token = response.getString("token");
                            sharedPreference_edit(token);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public HashMap<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("token", shared_token);
                    return headers;
                }
            };

            requestQueue.add(jsonObjectRequest);


        }
    }
    private  void sharedPreference_edit(String token){
        Context context = edit_profile.this;
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();

        Intent intent = new Intent(edit_profile.this , home_screen.class);
        startActivity(intent);
        finish();
    }
}
