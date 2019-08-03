package com.abhishek.buynsell;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class home_recyclerView_frag extends Fragment {

    RecyclerView recyclerView;

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
        String[] languages = {"Mountain Bike 1", "Mountain Bike 2", "Mountain Bike 3", "Mountain Bike 4", "Mountain Bike 5", "Mountain Bike 6", "Mountain Bike 7", "Mountain Bike 8", "Mountain Bike 9"};
        recyclerView.setAdapter(new recyclerViewAdapter(languages));
        return view;
    }


}
