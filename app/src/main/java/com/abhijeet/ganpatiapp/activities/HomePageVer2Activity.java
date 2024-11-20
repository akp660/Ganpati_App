package com.abhijeet.ganpatiapp.activities;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.abhijeet.ganpatiapp.R;

public class HomePageVer2Activity extends AppCompatActivity {

    LinearLayout layout, detailsText;
    CardView kundali, aarti, puja_list, booking, matching, whatshapp, playstore, mail;

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
        aarti = findViewById(R.id.aarti);
        puja_list = findViewById(R.id.cardView2);
        booking = findViewById(R.id.cardView13);
        whatshapp = findViewById(R.id.whatshapp);
        matching = findViewById(R.id.cardView12);





//whatshapp
        whatshapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://wa.me/+919798554810");
                triggerVibration();
            }
            public void gotourl(String s){
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });


// Booking OnClickListener
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a toast message
                Toast.makeText(HomePageVer2Activity.this, "Soon .....", Toast.LENGTH_SHORT).show();

                // Trigger vibration
                triggerVibration();
            }
        });



// Booking OnClickListener
        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show a toast message
                Toast.makeText(HomePageVer2Activity.this, "Soon .....", Toast.LENGTH_SHORT).show();

                // Trigger vibration
                triggerVibration();
            }
        });



// Aarti OnClickListener
        aarti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the Kundali_entry activity
                Intent intent = new Intent(HomePageVer2Activity.this, Aarti_list.class);
                startActivity(intent); // Use startActivity() for a single activity
// for animation
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                triggerVibration();
            }
        });


// Puja_List OnClickListener
        puja_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the Kundali_entry activity
                Intent intent = new Intent(HomePageVer2Activity.this, Puja_list.class);
                startActivity(intent); // Use startActivity() for a single activity
// for animation
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                triggerVibration();
            }
        });


// Kundali OnClickListener
        kundali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the Kundali_entry activity
                Intent intent = new Intent(HomePageVer2Activity.this, Kundali_entry.class);
                startActivity(intent); // Use startActivity() for a single activity
// for animation
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                triggerVibration();
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
        triggerVibration();
    }

    // Trigger vibration
    private void triggerVibration() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(50);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}
