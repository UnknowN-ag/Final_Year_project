package com.abhishek.buynsell;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class home_acc_frag extends Fragment {

//    private  String USER_URL = "http://192.168.1.106:3002/user";
//    private  String USER_URL = "http://dry-thicket-34134.herokuapp.com/user";
    private String USER_URL = home_screen.urlPrefix+"user";
    private String USER_PIC_URL = home_screen.urlPrefix+"profilepic";
    Button logout_btn;
    ImageView imageView;
    TextView name,dept_name,college_name,mobile_number;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private Bitmap bitmap;
    Uri selectedImage;
    String base64str;





    public home_acc_frag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.acc_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case  R.id.edit_profile:
                Intent intent = new Intent(getActivity(),edit_profile.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_acc_frag, container, false);

        name = view.findViewById(R.id.name);
        dept_name = view.findViewById(R.id.dept_name);
        college_name = view.findViewById(R.id.college_name);
        mobile_number = view.findViewById(R.id.mobile_number);
        imageView = view.findViewById(R.id.post_img);
        logout_btn = view.findViewById(R.id.logout_btn);

        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        final String shared_token = sharedPreferences.getString("token", "");


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, USER_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    Integer responseCode = response.getInt("responseCode");
                    if(responseCode == 200){
                        JSONObject user = response.getJSONObject("user");
                        name.setText(user.getString("fullname"));
                        dept_name.setText(user.getString("department"));
                        college_name.setText(user.getString("college"));
                        mobile_number.setText(user.getString("mobile"));
                        String profilePic = user.getString("profilePic");
                        Picasso.get()
                                .load(profilePic)
                                .resize(300, 300)
                                .centerCrop()
                                .into(imageView);
                        progressDialog.dismiss();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<>();
                headers.put("token", shared_token);
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

        logout_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Context context = getActivity();
                SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication",context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                getActivity().finish();

                Toast.makeText(getActivity(),"Logout Succesfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), login_screen.class);
                startActivity(intent);
            }
        });

        imageView = view.findViewById(R.id.profile_img);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        //permission not granted, request for permission
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup
                        requestPermissions(permission, PERMISSION_CODE);
                    }else{
                        //permission already granted
                        pickImgFromGallery();
                    }

                }else{
                    // verion less then marshmallow
                    pickImgFromGallery();
                }

            }
        });



        return view;
    }

    private void pickImgFromGallery(){
        //intent to pic img
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE);
    }

    // handle result of runtime permisiion
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImgFromGallery();
                }
                else{
                    Toast.makeText(getActivity(),"Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == getActivity().RESULT_OK ){
            switch (requestCode){
                case IMAGE_PICK_CODE:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

//                    imageView.setImageBitmap(bitmap);
                    base64str = imageToString(bitmap);
                    updateProfilePic(base64str);
                    break;
            }
        }
    }

    private  String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
    }

    void updateProfilePic(String base64str){
        Context context = getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", Context.MODE_PRIVATE);
        final String shared_token1 = sharedPreferences.getString("token", "");

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Updating...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        HashMap<String, String> params = new HashMap<>();
        params.put("file",base64str);

        JSONObject jsonObject = new JSONObject(params);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, USER_PIC_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//
                    Integer responsCode = response.getInt("responseCode");
                    if(responsCode == 200){
                        Toast.makeText(getActivity(), "Profile Pic Updated", Toast.LENGTH_LONG).show();
                        String token = response.getString("token");
                        Context context = getActivity();
                        SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication", context.MODE_PRIVATE );
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);

                        JSONObject user = response.getJSONObject("result");
                        String profilePic = user.getString("profilePic");
                        Picasso.get()
                                .load(profilePic)
                                .resize(300, 300)
                                .centerCrop()
                                .into(imageView);

                        progressDialog.dismiss();
                        editor.apply();

                    }else {
                        Toast.makeText(getActivity(), "Some Error While Updating Pic", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public HashMap<String, String> getHeaders() throws AuthFailureError{
                HashMap<String,String> headers = new HashMap<>();
                headers.put("token", shared_token1);
                return  headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }





}
