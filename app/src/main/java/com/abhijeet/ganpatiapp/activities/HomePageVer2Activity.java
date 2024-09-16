package com.abhijeet.ganpatiapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abhijeet.ganpatiapp.R;

public class HomePageVer2Activity extends AppCompatActivity {

    LinearLayout layout, detailsText;
    CardView kundali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_page_ver2);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

// Defining the views
        detailsText = findViewById(R.id.details);
        layout = findViewById(R.id.layout);
        kundali = findViewById(R.id.cardView11);

// Kundali OnClickListener
        kundali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the Kundali_entry activity
                Intent intent = new Intent(HomePageVer2Activity.this, Kundali_entry.class);
                startActivity(intent); // Use startActivity() for a single activity
// for animation
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                if (vibrator != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(50);
                    }
                }
            }
        });
    }

    public void expand(View view) {
        // Toggle the visibility of detailsText
        int visibility = (detailsText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        AutoTransition transition = new AutoTransition();
        transition.setDuration(200); // Set the transition duration

        // Apply the transition and change the visibility
        TransitionManager.beginDelayedTransition(layout, transition);
        detailsText.setVisibility(visibility);
    }
}
