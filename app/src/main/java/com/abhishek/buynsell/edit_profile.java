package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class edit_profile extends AppCompatActivity {

    Button save_btn;
    TextInputLayout full_name_register_layout,dept_name_register_layout,college_name_register_layout;
    TextInputEditText full_name_register,dept_name_register,college_name_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        save_btn = findViewById(R.id.save_btn);

        full_name_register_layout = findViewById(R.id.full_name_register_layout);
        dept_name_register_layout = findViewById(R.id.dept_name_register_layout);
        college_name_register_layout = findViewById(R.id.college_name_register_layout);
//        mobile_register_layout = findViewById(R.id.mobile_register_layout);

        full_name_register = findViewById(R.id.full_name_register);
        dept_name_register = findViewById(R.id.dept_name_register);
        college_name_register = findViewById(R.id.college_name_register);
        college_name_register.setKeyListener(null);
//        mobile_register = findViewById(R.id.mobile_register);

        save_btn.setOnClickListener(new View.OnClickListener() {
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
//        if(mobile_register.getText().toString().isEmpty()){
//            mobile_register_layout.setError("Shouldn't be Empty");
//            isValid = false;
//        }else {
//            mobile_register_layout.setError("");
//        }



        if(isValid){
            Intent intent = new Intent(edit_profile.this , home_screen.class);
            startActivity(intent);
            finish();
        }
    }
}
