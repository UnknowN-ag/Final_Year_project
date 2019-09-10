package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Post_description extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);

        getSupportActionBar().setTitle("ShopiFy");
        getSupportActionBar().setElevation(15);
        String productId = getIntent().getStringExtra("productId");

    }
}
