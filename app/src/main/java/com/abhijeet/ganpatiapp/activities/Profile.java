package com.abhijeet.ganpatiapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.abhijeet.ganpatiapp.R;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Profile extends AppCompatActivity {

    ImageView image;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    StorageReference reference;
    CardView sendbtn;
    EditText message;

    String messagestr, phonestr = "";



    //TextView nameTextView, emailTextView, phoneTextView, addressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        //nameTextView = findViewById(R.id.textView10);
        //emailTextView = findViewById(R.id.textView11);
        //phoneTextView = findViewById(R.id.textView12);
        //addressTextView = findViewById(R.id.textView13);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        reference = FirebaseStorage.getInstance().getReference("Priest_Profile/Profile_Pic.png");
        image = findViewById(R.id.imageView4);


        // setting image in image view from firebase.

        try {
            File file = File.createTempFile("tempfile", ".png");
            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        CardView back = findViewById(R.id.materialCardView6);
        back.setOnClickListener(view -> back());

//      DatabaseReference ref = database.getReference().child(firebaseAuth.getUid());
        DatabaseReference ref = database.getReference().child("Users").child(firebaseAuth.getUid());



        /*ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//              Toast.makeText(Profile.this, dataSnapshot.child("Name").getValue(String.class), Toast.LENGTH_SHORT).show();
                nameTextView.setText(dataSnapshot.child("Name").getValue(String.class));
                emailTextView.setText(dataSnapshot.child("Email").getValue(String.class));
                phoneTextView.setText(dataSnapshot.child("Phone").getValue(String.class));
                addressTextView.setText(dataSnapshot.child("Address").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


        message = findViewById(R.id.editText);
        sendbtn = findViewById(R.id.sendbtn);


//        sendbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Profile.this, String.valueOf(iswhatsappInstalled()), Toast.LENGTH_SHORT).show();
//            }
//        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagestr = message.getText().toString();

                if(!messagestr.isEmpty()){

                    if(iswhatsappInstalled()){

                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+919155182211&text=" + messagestr));
                        startActivity(i);
                        message.setText("");

                    }else {

                        Toast.makeText(Profile.this,"Whatsapp is not installed",Toast.LENGTH_SHORT).show();

                    }

                }else {

                    Toast.makeText(Profile.this,"Write your Query in the box and try again.",Toast.LENGTH_LONG).show();

                }

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

    private boolean iswhatsappInstalled() {
        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled=false;
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }

//        Toast.makeText(this, String.valueOf(whatsappInstalled), Toast.LENGTH_SHORT).show();

        return whatsappInstalled;
    }





    public void back(){
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
// for animation
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }
}