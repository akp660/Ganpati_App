package com.abhijeet.ganpatiapp.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.abhijeet.ganpatiapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Aarti_view extends AppCompatActivity {

    TextView name, aarti;
    FirebaseDatabase database;
    DatabaseReference reference;
    CardView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aarti_view);

        name = findViewById(R.id.textView17);
        aarti = findViewById(R.id.aarti);
        backButton = findViewById(R.id.backButton);

        database = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        String value = intent.getStringExtra("name");
        name.setText(value);

        reference = FirebaseDatabase.getInstance().getReference().child("Aarti").child(value);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String x = snapshot.getValue(String.class);
                String y = x.replace("@","\n");
                aarti.setText(y);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Vibrate for 100 milliseconds
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(50);
        }

        super.onBackPressed();
    }
}