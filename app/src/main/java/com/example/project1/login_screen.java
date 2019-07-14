package com.example.project1;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class login_screen extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);



        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
    public void change_fragment(View view){
        Fragment fragment;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        if(view == findViewById(R.id.login_link)){
            fragment = new login_frag();
            ft.replace(R.id.fragment_place, fragment);
        }
        if(view == findViewById(R.id.signup_link)){
            fragment = new signup_frag();
            ft.replace(R.id.fragment_place, fragment);
            ft.addToBackStack(null);

        }
        ft.commit();
    }




}
