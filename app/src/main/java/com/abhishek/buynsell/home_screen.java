package com.abhishek.buynsell;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class home_screen extends AppCompatActivity {
    FrameLayout frameLayout;
    private TextView mTextMessage;

    protected  static  String urlPrefix = "http://192.168.1.107:3002/";
//    protected  static  String urlPrefix = "http://dry-thicket-34134.herokuapp.com/";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setRecyclerFragment(new home_recyclerView_frag());
                    return true;
                case R.id.navigation_dashboard:
                    setPostFragment(new home_post_frag());
                    return true;
                case R.id.navigation_account:
                    setAccountFragment(new home_acc_frag());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        getSupportActionBar().setTitle("SelloFy");
        getSupportActionBar().setElevation(15);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        frameLayout = findViewById(R.id.fragment_home_place);
        setFragmentInitially(new  home_recyclerView_frag());
    }

    private  void setFragmentInitially(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        ft.commit();
    }
    private  void setRecyclerFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        ft.commit();
    }
    private  void setPostFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        ft.commit();
    }
    private  void setAccountFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(frameLayout.getId(), fragment);
        ft.commit();
    }


}
