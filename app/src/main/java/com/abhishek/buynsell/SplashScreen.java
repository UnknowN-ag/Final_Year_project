package com.abhishek.buynsell;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    private  static int SPLASH_TIME_OUT = 1000;
//    private  String USER_URL = "http://dry-thicket-34134.herokuapp.com/user";
    private String USER_URL = home_screen.urlPrefix+"user";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();



        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                final Context context = SplashScreen.this;
                SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication",context.MODE_PRIVATE);
                final String token = sharedPreferences.getString("token", "");

                RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Integer responseCode = response.getInt("responseCode");
                            Log.d("responseCode",responseCode.toString());
                            if(responseCode == 200){
                                Intent intent = new Intent(context, home_screen.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(responseCode == 300){
                                Intent intent = new Intent(context, registration_afterSignup.class);
                                startActivity(intent);
                                finish();
                            }else  if(responseCode == 100){
                                Intent intent = new Intent(context, login_screen.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Intent intent = new Intent(SplashScreen.this, login_screen.class);
                        startActivity(intent);
                        finish();
                    }
                }){
                    @Override
                    public HashMap<String, String> getHeaders() throws AuthFailureError{
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("token", token);
                        return  headers;
                    }
                };

                requestQueue.add(jsonObjectRequest);
            }

        },SPLASH_TIME_OUT );
    }
}
