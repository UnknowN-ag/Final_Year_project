package com.abhishek.buynsell;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class home_post_frag extends Fragment {

    ImageView imageView;
    Spinner dropdown_post_price;
    TextInputLayout price_input_layout;
    TextInputEditText price_input;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


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
        price_input = view.findViewById(R.id.price_input);
        dropdown_post_price = view.findViewById(R.id.dropdown_post_price);

        price_input_layout = view.findViewById(R.id.price_input_layout);

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
               imageView.setImageURI(selectedImage);
               break;
         }
        }
    }
}
