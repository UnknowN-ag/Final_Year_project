package com.abhishek.buynsell;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class signup_frag extends Fragment {

    TextInputLayout signup_mobile_input_layout;
    TextInputLayout signup_password_input_layout;
    TextInputEditText signup_mobile_input;
    TextInputEditText signup_password_input;
    Button signup_btn;

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
        if((signup_mobile_input.getText().toString().isEmpty()) || (!signup_mobile_input.getText().toString().equals("1111"))){
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
            Toast.makeText(getActivity(),"Signup Succesfull", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), registration_afterSignup.class);
            startActivity(intent);
        }

    }


}
