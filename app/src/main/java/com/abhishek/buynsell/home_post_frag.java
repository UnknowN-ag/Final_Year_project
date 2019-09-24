package com.abhishek.buynsell;

import android.Manifest;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class home_post_frag extends Fragment {

//    private  String POST_URL = "http://192.168.1.106:3002/post";
//    private  String POST_URL = "http://dry-thicket-34134.herokuapp.com/post";
    private String POST_URL = home_screen.urlPrefix+"post";
    ImageView imageView;
    Spinner dropdown_post_price;
    TextInputLayout product_name_input_layout,product_desc_input_layout,price_input_layout;
    TextInputEditText product_name_input,product_desc_input,price_input;
    Button post_btn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private Bitmap bitmap;


    public home_post_frag() {
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
        View view = inflater.inflate(R.layout.fragment_home_post_frag, container, false);

        imageView = view.findViewById(R.id.post_img);

        dropdown_post_price = view.findViewById(R.id.dropdown_post_price);

        product_name_input_layout = view.findViewById(R.id.product_name_input_layout);
        product_desc_input_layout = view.findViewById(R.id.product_desc_input_layout);
        price_input_layout = view.findViewById(R.id.price_input_layout);

        product_name_input = view.findViewById(R.id.product_name_input);
        product_desc_input = view.findViewById(R.id.product_desc_input);
        price_input = view.findViewById(R.id.price_input);

        post_btn  = view.findViewById(R.id.post_btn);

        post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_post();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
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

        dropdown_post_price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(dropdown_post_price.getSelectedItem().equals("Paid")){
                    price_input_layout.setVisibility(View.VISIBLE);
                }else{
                    price_input_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        String spinner_value = dropdown_post_price.getSelectedItem().toString();
//        price_input.setText(spinner_value);
//        if(spinner_value.equals("Paid")){
//            price_input_layout.setVisibility(View.GONE);
//        }else{
//            price_input_layout.setVisibility(View.VISIBLE);
//        }

        return  view;
    }

    private void pickImgFromGallery(){
        //intent to pic img
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
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
               Uri selectedImage = data.getData();
                               Log.d("selectedImagePATH",selectedImage.toString());
                   try {
                       bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                       Log.d("bitmap",bitmap.toString());
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
                Log.d("bitmap",bitmap.toString());
               imageView.setImageBitmap(bitmap);
               Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
               break;
         }
        }
    }

    private  String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayInputStream);
        byte[] imgBytes = byteArrayInputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private  void uploadImage(){

    }

    private void validate_post(){
        boolean isValid = true;
        if((product_name_input.getText().toString().isEmpty())){
            product_name_input_layout.setError("Product Name Required");
            isValid = false;
        }else {
            product_name_input_layout.setError("");
        }
        if((product_desc_input.getText().toString().isEmpty()) ){
            product_desc_input_layout.setError("Product Description Required");
            isValid = false;
        }else {
            product_desc_input_layout.setError("");
        }
        String paymentMethod = dropdown_post_price.getSelectedItem().toString();
        if(paymentMethod.equals("Paid")){
            if((price_input.getText().toString().isEmpty()) ){
                price_input_layout.setError("Shouldn't be Empty");
                isValid = false;
            }else {
                price_input_layout.setError("");
            }
        }

        if(isValid){

            final Context context = getActivity();
            SharedPreferences sharedPreferences = context.getSharedPreferences("Authentication",context.MODE_PRIVATE);
            final String shared_token = sharedPreferences.getString("token", "");

            String paymentType = "Free";
            if(paymentMethod.equals("Free")){
                paymentType = "Free";
            }else if(paymentMethod.equals("Paid")){
                paymentType = "Paid";
            }

            HashMap<String, String> params = new HashMap<>();
            params.put("nameOfProduct", product_name_input.getText().toString());
            params.put("description", product_desc_input.getText().toString());
            params.put("paymentType", paymentType);
            params.put("price", price_input.getText().toString());

            JSONObject jsonObject = new JSONObject(params);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, POST_URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Integer responsCode = response.getInt("responseCode");
                        if(responsCode == 200){
                            product_name_input.setText("");
                            product_desc_input.setText("");
                            price_input.setText("");
                            Toast.makeText(getActivity(), "Post Created", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Some Error While Creating Post", Toast.LENGTH_SHORT).show();
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
                    headers.put("token", shared_token);
                    return  headers;
                }
            };
            requestQueue.add(jsonObjectRequest);



        }
    }
}
