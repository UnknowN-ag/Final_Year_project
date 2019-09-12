package com.abhishek.buynsell;


import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class home_recyclerView_frag extends Fragment {

    private  String ALL_POSTS_URL = "http://192.168.1.106:3002/viewallpost";

    RecyclerView recyclerView;
    List<LauncherActivity.ListItem> listItems;

    List<Post> Post = new  ArrayList<>();;

    public home_recyclerView_frag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_recycler_view_frag, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewAllPosts();


        return view;
    }

    private  void viewAllPosts(){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ALL_POSTS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Integer responseCode = response.getInt("responseCode");
                    if(responseCode == 200){
                        progressDialog.dismiss();
                        JSONArray jsonArray = response.getJSONArray("post");
                        for(int i = 0; i<jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String nameOfProduct = jsonObject.getString("nameOfProduct");
                            String paymentType = jsonObject.getString("paymentType");
                            String price = jsonObject.getString("price");
                            String productId = jsonObject.getString("_id");

                            Post.add(new Post(nameOfProduct, paymentType, price, productId));


                        }
                        recyclerView.setAdapter(new recyclerViewAdapter(getActivity(),Post));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }


}
