package com.abhijeet.ganpatiapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.activities.Aarti_list;
import com.abhijeet.ganpatiapp.activities.Aarti_view;


public class AartiFragment extends Fragment {

    TextView text;



    public AartiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_aarti, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Aarti_list.class);
                intent.putExtra("name","Aarti Sangrah");
                v.getContext().startActivity(intent);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }
}