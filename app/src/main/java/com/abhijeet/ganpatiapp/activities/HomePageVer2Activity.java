package com.abhijeet.ganpatiapp.activities;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abhijeet.ganpatiapp.R;

public class HomePageVer2Activity extends AppCompatActivity {

    LinearLayout layout, detailsText; // Changed LinearLayout to ConstraintLayout

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

        detailsText = findViewById(R.id.details);
        layout = findViewById(R.id.layout); // Correctly assigning as ConstraintLayout
    }

    public void expand(View view) {
        int v = (detailsText.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        AutoTransition transition = new AutoTransition();
        transition.setDuration(200); // Set duration in milliseconds (500ms = 0.5 seconds)

        TransitionManager.beginDelayedTransition(layout, transition);
        detailsText.setVisibility(v);
    }

}
