package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

public class Post_description extends AppCompatActivity {

    private String PROD_URL = "http://192.168.1.106:3002/viewpostbyid";

    TextView productName,paymentType,price,postDescription,userName,userDept,mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);

        productName = findViewById(R.id.productName);
        paymentType = findViewById(R.id.paymentType);
        price = findViewById(R.id.price);
        postDescription = findViewById(R.id.postDescription);
        userName = findViewById(R.id.userName);
        userDept = findViewById(R.id.userDept);
        mobileNumber = findViewById(R.id.mobileNumber);

        getSupportActionBar().setTitle("ShopiFy");
        getSupportActionBar().setElevation(15);
        String productId = getIntent().getStringExtra("productId");

        HashMap<String,String> params = new HashMap<>();
        params.put("productId", productId);
        JSONObject jsonObject = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(Post_description.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, PROD_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer responseCode = response.getInt("responseCode");
                    if(responseCode == 200){
                        JSONArray jsonArray = response.getJSONArray("product");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        productName.setText(jsonObject1.getString("nameOfProduct"));
                        paymentType.setText(jsonObject1.getString("paymentType"));
                        if(jsonObject1.getString("paymentType").equals("Free")){
                            price.setVisibility(View.INVISIBLE);
                        }
                        price.setText(jsonObject1.getString("price"));
                        postDescription.setText(jsonObject1.getString("description"));

                        JSONArray jsonArray1 = jsonObject1.getJSONArray("user");
                        JSONObject jsonObject2 = jsonArray1.getJSONObject(0);
                        userName.setText(jsonObject2.getString("fullname"));
                        userDept.setText(jsonObject2.getString("department"));
                        mobileNumber.setText(jsonObject2.getString("mobile"));

                    }else{
                        Toast.makeText(Post_description.this, "ProductID Required", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Post_description.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
