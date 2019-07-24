package com.abhishek.buynsell;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.TextView;

public class home_screen extends AppCompatActivity {
    private TextView mTextMessage;
    RecyclerView recyclerView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        recyclerView = findViewById(R.id.recycler_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String[] languages = {"Mountain Bike 1", "Mountain Bike 2", "Mountain Bike 3", "Mountain Bike 4", "Mountain Bike 5", "Mountain Bike 6", "Mountain Bike 7", "Mountain Bike 8", "Mountain Bike 9"};
        recyclerView.setAdapter(new recyclerViewAdapter(languages));
    }

}
