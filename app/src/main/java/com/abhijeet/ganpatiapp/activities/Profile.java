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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Profile extends AppCompatActivity {

    ImageView image;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    StorageReference reference;
    CardView sendbtn;
    EditText message;

    String messagestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseStorage.getInstance();
        reference = FirebaseStorage.getInstance().getReference("Priest_Profile/Profile_Pic.png");
        image = findViewById(R.id.imageView4);

        // Name of the image file to be saved locally
        String LOCAL_IMAGE_NAME = "Profile_Pic.png";
        File localFile = new File(getFilesDir(), LOCAL_IMAGE_NAME);

        if (localFile.exists()) {
            // Load image from local storage if it exists
            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
            image.setImageBitmap(bitmap);
        } else {
            // Download the image from Firebase Storage and save it locally
            downloadAndSaveImage(localFile);
        }

        CardView back = findViewById(R.id.materialCardView6);
        back.setOnClickListener(view -> back());

        database.getReference().child("Users").child(Objects.requireNonNull(firebaseAuth.getUid()));

        message = findViewById(R.id.editText);
        sendbtn = findViewById(R.id.sendbtn);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagestr = message.getText().toString();

                if (!messagestr.isEmpty()) {
                    if (iswhatsappInstalled()) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+919798554810&text=" + messagestr));
                        startActivity(i);
                        message.setText("");
                    } else {
                        Toast.makeText(Profile.this, "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Profile.this, "Write your Query in the box and try again.", Toast.LENGTH_LONG).show();
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

    private void downloadAndSaveImage(File localFile) {
        try {
            reference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                    saveImageLocally(bitmap, localFile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Profile.this, "Error downloading image", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImageLocally(Bitmap bitmap, File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean iswhatsappInstalled() {
        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled = false;
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        return whatsappInstalled;
    }

    public void back() {
        Intent intent = new Intent(Profile.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        vibrate();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void vibrate() {
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
