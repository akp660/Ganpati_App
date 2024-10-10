package com.abhijeet.ganpatiapp.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.adapters.Aarti_List_Adapter;
import com.abhijeet.ganpatiapp.modelclass.Aarti_List_Model_Class;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Aarti_list extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView aartiRecyclerView;
    LinearLayoutManager layoutManager;
    List<Aarti_List_Model_Class> list;
    Aarti_List_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aarti_list);

        aartiRecyclerView = findViewById(R.id.aartiRecyclerView);
        list = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Aarti");

        Log.d(TAG, "onCreate: first print");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: third");
                for (DataSnapshot ds : snapshot.getChildren()) {
                    list.add(new Aarti_List_Model_Class(ds.getKey()));
                    Log.d(TAG, "onDataChange: " + ds.getKey());
                }
                Log.d(TAG, "onDataChange: fourth");
                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "DatabaseError: " + error.getMessage());
            }
        });
    }

    // Initialize RecyclerView for displaying the list of Aartis
    public void initRecyclerView() {
        aartiRecyclerView = findViewById(R.id.aartiRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        aartiRecyclerView.setLayoutManager(layoutManager);
        adapter = new Aarti_List_Adapter(list);
        aartiRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Override the onBackPressed to handle the back button press
    @Override
    public void onBackPressed() {
        // Handle the back press like you would in the back button click
        super.onBackPressed();
        Intent intent = new Intent(Aarti_list.this, HomePageVer2Activity.class);
        startActivity(intent);

        // Trigger the slide-in animation
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
