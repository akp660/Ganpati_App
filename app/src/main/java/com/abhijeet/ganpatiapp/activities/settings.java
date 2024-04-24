package com.abhijeet.ganpatiapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.bottomsheets.pictureFragment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity implements pictureFragment.transferPicture {

    CardView logOutCard, cardView;
    MaterialCardView addPictureButton;

    FirebaseAuth firebaseAuth;

    FirebaseDatabase database;
    DatabaseReference reference;
    TextView name,address,email,phone;
    ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        CardView  cardView = findViewById(R.id.checkForUpdate);

        logOutCard = findViewById(R.id.logoutCard);
        name = findViewById(R.id.textView22);
        address = findViewById(R.id.textView23);
        email = findViewById(R.id.textView24);
        phone = findViewById(R.id.textView25);
        addPictureButton = findViewById(R.id.addPictureButton);
        profilePicture = findViewById(R.id.profilePicture);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid());

        CardView back = findViewById(R.id.backButton);
        back.setOnClickListener(view -> back());

        loadProfileImage();



        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new pictureFragment(settings.this::transferImage);
                bottomSheetDialogFragment.show(getSupportFragmentManager(),bottomSheetDialogFragment.getTag());
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("Name").getValue(String.class));
                address.setText(snapshot.child("Address").getValue(String.class));
                email.setText(snapshot.child("Email").getValue(String.class));
                phone.setText(snapshot.child("Phone").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logOutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent = new Intent(settings.this, Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/details?id=com.abhijeet.ganpatiapp"; // Replace with your desired URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


    }




    public void back(){
        Intent intent = new Intent(settings.this, MainActivity.class);
        startActivity(intent);
// for animation
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
// for vibration
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(50);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        // for animation
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void loadProfileImage(){
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
            String profile = sharedPreferences.getString("profilePicture",null);
            profilePicture.setImageBitmap(stringToBitmap(profile));
        }

        catch (Exception e){
            Log.d("error", "loadProfileImage: " + e.getMessage());
        }
    }

    public Bitmap stringToBitmap(String s){
        byte[] byteArray = android.util.Base64.decode(s, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public void transferImage(String image) {
        profilePicture.setImageBitmap(stringToBitmap(image));
    }
}