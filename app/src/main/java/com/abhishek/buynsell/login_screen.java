package com.abhishek.buynsell;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class login_screen extends AppCompatActivity {

    FrameLayout fragment_layout;
    TextView signup_link;
    TextView login_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        signup_link = findViewById(R.id.signup_link);
        login_link = findViewById(R.id.login_link);

//      ----------------by default fragment in login screen-----------------
        fragment_layout = (FrameLayout) findViewById(R.id.fragment_place);
        setFragment(new login_frag());
//      ----------------by default fragment in login screen close-----------------

//      ----------------fragment change logic-------------------------------
        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignUpFragment(new signup_frag());
            }
        });
        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLogInFragment(new login_frag());
            }
        });
//      ----------------fragment change logic close-------------------------------

//      -----------------------to hide actionBar-------------------------
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    } // onCreate method
//      -----------------------to hide actionBar close-------------------------


    private void setFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(fragment_layout.getId(), fragment);
        ft.commit();
    }
    private void setSignUpFragment(Fragment fragment_signup){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction  ft = fm.beginTransaction();
        ft.replace(fragment_layout.getId(), fragment_signup);
        ft.commit();
    }
    private void setLogInFragment(Fragment fragment_login){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(fragment_layout.getId(), fragment_login);
        ft.commit();
    }



//    public void change_fragment(View view){
//        Fragment fragment;
//
//        if( view == findViewById(R.id.login_link)){
//
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            fragment = new login_frag();
//            ft.addToBackStack(null);
//            ft.replace(R.id.fragment_place, fragment);
//            ft.commit();
//
//        }
//        if(view == findViewById(R.id.signup_link)){
//            FragmentManager fm = getSupportFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            fragment = new signup_frag();
//            ft.replace(R.id.fragment_place, fragment);
//            ft.addToBackStack(null);
//            ft.commit();
//
//        }
//
//    }




}
