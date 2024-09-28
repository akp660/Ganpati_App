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

    LinearLayout layout, detailsText, app_info, app_infp_detail;
    CardView kundali, aarti, puja_list, booking, whatshapp, playstore, mail;

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
        app_info = findViewById(R.id.layout_1);
        app_infp_detail = findViewById(R.id.detail_1);
        puja_list = findViewById(R.id.cardView2);
        booking = findViewById(R.id.cardView13);
        whatshapp = findViewById(R.id.whatshapp);
        playstore = findViewById(R.id.cardView15);
        mail = findViewById(R.id.cardView18);









// Mail OnClickListener
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerVibration();
                openEmailClient();
            }
            // Define the openEmailClient method outside of the onClick method
            private void openEmailClient() {
                // Create the intent to open the email client
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822"); // Set MIME type to target email clients
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"abhijeetpandeydhn@gmail.com"}); // Replace with the desired email address
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry from Ganpati App User"); // Set the email subject
                emailIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey,\n\n" +
                                "I am a Ganpati App user, and I wanted to get in touch regarding \n\n" +
                                "[your query or concern]. \n\n " +
                                "Thank you!\n\n" +
                                "Best regards,\n" +
                                "[Your Name]"); // Set the email body text

                // Try to open an email app, and handle the case where no app is found
                try {
                    startActivity(Intent.createChooser(emailIntent, "Choose an email client"));
                } catch (android.content.ActivityNotFoundException ex) {
                    // Handle the case when no email app is available
                    Toast.makeText(HomePageVer2Activity.this, "No email app found", Toast.LENGTH_SHORT).show();
                }
            }

        });







//playstore
        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://play.google.com/store/apps/details?id=com.abhijeet.ganpatiapp");
                triggerVibration();
            }
            public void gotourl(String s){
                Uri uri = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });


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
                // Create an Intent to start the Kundali_entry activity
                Intent intent = new Intent(HomePageVer2Activity.this, booking.class);
                startActivity(intent); // Use startActivity() for a single activity
// for animation
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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


// Set OnClickListener for the app_info to trigger the expansion
        app_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerVibration();
                expandAppInfo(view); // Toggle app_infp_detail visibility on click
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

    public void expandAppInfo(View view) {
        // Toggle the visibility of app_infp_detail
        int visibility = (app_infp_detail.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        AutoTransition transition = new AutoTransition();
        transition.setDuration(200); // Set the transition duration

        // Apply the transition and change the visibility
        TransitionManager.beginDelayedTransition(app_info, transition);
        app_infp_detail.setVisibility(visibility);
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

}
