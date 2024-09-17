package com.abhijeet.ganpatiapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aarti_view);

        name = findViewById(R.id.textView17); // Aarti name TextView
        aarti = findViewById(R.id.aarti); // Aarti content TextView

        database = FirebaseDatabase.getInstance();

        // Get the Aarti name passed from the adapter
        Intent intent = getIntent();
        String value = intent.getStringExtra("name");
        name.setText(value);

        // Reference to the specific Aarti content in Firebase
        assert value != null;
        reference = FirebaseDatabase.getInstance().getReference().child("Aarti").child(value);

        // Fetch and display the Aarti content from Firebase
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String x = snapshot.getValue(String.class);
                assert x != null;
                String y = x.replace("@", "\n"); // Replace "@" with new line
                aarti.setText(y);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any error here if needed
            }
        });
    }

    // Override the onBackPressed to handle custom back press logic
    @Override
    public void onBackPressed() {
        // Handle the back press like you would in the back button click
        super.onBackPressed();
        Intent intent = new Intent(Aarti_view.this, Aarti_list.class);
        startActivity(intent);

        // Trigger the slide-in animation
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

}
