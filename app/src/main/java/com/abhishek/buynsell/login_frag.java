package com.abhishek.buynsell;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class login_frag extends Fragment {

    TextInputLayout login_mobile_input_layout;
    TextInputLayout login_password_input_layout;
    TextInputEditText login_mobile_input;
    TextInputEditText login_password_input;
    Button login_btn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("login_Data",context.MODE_PRIVATE);
        int mobile = sharedPreferences.getInt("mobile",0);
        if(mobile>0){
            Intent intent = new Intent(getActivity(),home_screen.class);
            startActivity(intent);
        }


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

//    private  void validate_login(){
//        boolean isValid = true;
//        if((login_mobile_input.getText().toString().isEmpty()) || (!login_mobile_input.getText().toString().equals("1111"))){
//            login_mobile_input_layout.setError("Please Enter Valid Number");
//            isValid = false;
//        }else {
//            login_mobile_input_layout.setError("");
//        }
//
//        if((login_password_input.getText().toString().isEmpty()) || (!login_password_input.getText().toString().equals("admin")) ){
//            login_password_input_layout.setError("Please Enter Valid Password");
//            isValid = false;
//        }else {
//            login_mobile_input_layout.setError("");
//        }
//
//    }


    private void validate_login(){
        boolean isValid = true;
        if((login_mobile_input.getText().toString().isEmpty()) || (!login_mobile_input.getText().toString().equals("1111"))){
            login_mobile_input_layout.setError("Please Enter Valid Number");
            isValid = false;
        }else {
            login_mobile_input_layout.setError("");
        }

        if((login_password_input.getText().toString().isEmpty()) || (!login_password_input.getText().toString().equals("admin")) ){
            login_password_input_layout.setError("Please Enter Valid Password");
            isValid = false;
        }else {
            login_password_input_layout.setError("");
        }

        if(isValid){
            sharedPreference_login();
        }

    }
    private  void sharedPreference_login(){
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("login_Data", context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("mobile", Integer.parseInt(login_mobile_input.getText().toString()));
        editor.apply();

        Toast.makeText(getActivity(),"Login Succesfull", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), home_screen.class);
        startActivity(intent);
        getActivity().finish();
    }



}
