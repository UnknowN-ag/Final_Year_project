package com.example.project1;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class login_screen extends AppCompatActivity {

    TextInputLayout mobile_input_layout;
    TextInputLayout password_input_layout;
    TextInputEditText mobile_input;
    TextInputEditText password_input;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mobile_input_layout = (TextInputLayout) findViewById(R.id.mobile_input_layout);
        password_input_layout = (TextInputLayout) findViewById(R.id.password_input_layout);
        mobile_input = (TextInputEditText) findViewById(R.id.mobile_input);
        mobile_input = (TextInputEditText) findViewById(R.id.mobile_input);
        password_input = (TextInputEditText) findViewById(R.id.password_input);
        login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                validate_login();
            }
        });

    }

    private void validate_login(){
        boolean isValid = true;
        if((mobile_input.getText().toString().isEmpty()) || (!mobile_input.getText().toString().equals("1111"))){
            mobile_input_layout.setError("Please Enter Valid Number");
            isValid = false;
        }else {
            mobile_input_layout.setError("");
        }

        if((password_input.getText().toString().isEmpty()) || (!password_input.getText().toString().equals("admin")) ){
            mobile_input_layout.setError("Please Input Valid Password");
            isValid = false;
        }else {
            password_input_layout.setError("");
        }

        if(isValid){
            Toast.makeText(login_screen.this,"Login Succesfull", Toast.LENGTH_SHORT).show();
        }

    }


}
