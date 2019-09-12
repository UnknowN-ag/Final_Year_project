package com.abhishek.buynsell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.HashMap;


public class login_frag extends Fragment {

    TextInputLayout login_mobile_input_layout;
    TextInputLayout login_password_input_layout;
    TextInputEditText login_mobile_input;
    TextInputEditText login_password_input;
    Button login_btn;

    String URL = "http://192.168.1.106:3002/getsigneduser";
    private  String USER_URL = "http://192.168.1.106:3002/user";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login_frag, container, false);

        login_mobile_input_layout = (TextInputLayout) view.findViewById(R.id.login_mobile_input_layout);
        login_mobile_input = (TextInputEditText) view.findViewById(R.id.login_mobile_input);
        login_password_input_layout = (TextInputLayout) view.findViewById(R.id.login_password_input_layout);
        login_password_input = (TextInputEditText) view.findViewById(R.id.login_password_input);
        login_btn = (Button) view.findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validate_login();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void validate_login(){
        boolean isValid = true;
        if((login_mobile_input.getText().toString().isEmpty()) || (login_mobile_input.getText().toString().length()!=10)){
            login_mobile_input_layout.setError("Please Enter Valid Number");
            isValid = false;
        }else {
            login_mobile_input_layout.setError("");
        }
        if((login_password_input.getText().toString().isEmpty()) ){
            login_password_input_layout.setError("Please Enter Valid Password");
            isValid = false;
        }else {
            login_password_input_layout.setError("");
        }

        if(isValid){
            String mobile = login_mobile_input.getText().toString();
            String password = login_password_input.getText().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", mobile);
            params.put("password", password);
            JSONObject jsonObject = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Integer responseCode = response.getInt("responseCode");
                        if(responseCode == 200){
                            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                            String token = response.getString("token");
                            sharedPreference_login(token,responseCode);
                        }
                        else if(responseCode == 300){
                            Toast.makeText(getActivity(), "not verified", Toast.LENGTH_SHORT).show();
                            String token = response.getString("token");
                            sharedPreference_login(token,responseCode);
                        }
                        else if(responseCode == 500){
                            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getActivity(), "API not Responding", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jsonObjectRequest);
        }
    }
    private  void sharedPreference_login(String token, Integer responseCode){
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();

        if(responseCode == 200){
            Intent intent = new Intent(getActivity(), home_screen.class);
            startActivity(intent);
            getActivity().finish();
        }
        if(responseCode == 300){
            Intent intent = new Intent(getActivity(), registration_afterSignup.class);
            startActivity(intent);
            getActivity().finish();
        }
    }



}
