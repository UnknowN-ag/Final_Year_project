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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class signup_frag extends Fragment {

    TextInputLayout signup_mobile_input_layout;
    TextInputLayout signup_password_input_layout;
    TextInputEditText signup_mobile_input;
    TextInputEditText signup_password_input;
    Button signup_btn;

    private  String SIGNUP_URL = "http://192.168.1.106:3002/signup";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_frag, container, false);

        signup_mobile_input_layout = (TextInputLayout) view.findViewById(R.id.signup_mobile_input_layout);
        signup_mobile_input = (TextInputEditText) view.findViewById(R.id.signup_mobile_input);
        signup_password_input_layout = (TextInputLayout) view.findViewById(R.id.signup_password_input_layout);
        signup_password_input = (TextInputEditText) view.findViewById(R.id.signup_password_input);
        signup_btn = (Button) view.findViewById(R.id.signup_btn);

        signup_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                validate_signup();
            }
        });

        return view;
    }

    private void validate_signup(){
        boolean isValid = true;
        if((signup_mobile_input.getText().toString().isEmpty()) || signup_mobile_input.getText().toString().length()!=10){
            signup_mobile_input_layout.setError("Please Enter Valid Number");
            isValid = false;
        }else {
            signup_mobile_input_layout.setError("");
        }

        if((signup_password_input.getText().toString().isEmpty()) || (!signup_password_input.getText().toString().equals("admin")) ){
            signup_password_input_layout.setError("Please Enter Valid Password");
            isValid = false;
        }else {
            signup_password_input_layout.setError("");
        }

        if(isValid){
//            sharedPreference_signUp();
            HashMap<String, String> params = new HashMap<>();
            params.put("mobile", signup_mobile_input.getText().toString());
            params.put("password", signup_password_input.getText().toString());
            Log.d("params", params.toString());
            JSONObject jsonObject = new JSONObject(params);
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SIGNUP_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try{
                        Integer responseCode = response.getInt("responseCode");
                        if(responseCode == 200){
                            Toast.makeText(getActivity(), "Sign Up Successfull", Toast.LENGTH_SHORT).show();
                            String token = response.getString("token");
                            sharedPreference_signUp(token);
                        }else if(responseCode == 700){
                            Toast.makeText(getActivity(), "User Already Exist", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "err", Toast.LENGTH_LONG).show();
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
    private void sharedPreference_signUp(String token){
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();

        Intent intent = new Intent(getActivity(), registration_afterSignup.class);
        startActivity(intent);
        getActivity().finish();
    }


}
