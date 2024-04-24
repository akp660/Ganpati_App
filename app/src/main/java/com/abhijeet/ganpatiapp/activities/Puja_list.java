package com.abhijeet.ganpatiapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.adapters.Aarti_List_Adapter;
import com.abhijeet.ganpatiapp.adapters.PujaListAdapter;
import com.abhijeet.ganpatiapp.modelclass.Aarti_List_Model_Class;
import com.abhijeet.ganpatiapp.modelclass.PujaListModelClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Puja_list extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView pujaListRecyclerView;
    LinearLayoutManager layoutManager;
    List<PujaListModelClass> list;
    PujaListAdapter adapter;
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puja_list);

        backButton = findViewById(R.id.backButton);

        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Puja");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    list.add(new PujaListModelClass(ds.getKey()));
                }

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void initRecyclerView() {
        pujaListRecyclerView = findViewById(R.id.puja_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        pujaListRecyclerView.setLayoutManager(layoutManager);
        adapter = new PujaListAdapter(list);
        pujaListRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}