package com.abhijeet.ganpatiapp.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.abhijeet.ganpatiapp.R;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.abhijeet.ganpatiapp.adapters.SpotifyLinkAdapter;
import com.abhijeet.ganpatiapp.adapters.ViewPagerAdapter;
import com.abhijeet.ganpatiapp.modelclass.PackageChecker;
import com.abhijeet.ganpatiapp.modelclass.SpotifyLinkModelClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    CardView chat;
    TextView mainText;

    ImageView image, buttonTest;
    StorageReference reference;
    ScrollView scrollView;
    ViewPager viewPager;
    String personalUID="";
    RecyclerView aartiRecyclerView;
    SpotifyLinkAdapter spotifyAdapter;
    LinearLayoutManager layoutManager;
    List<SpotifyLinkModelClass> dataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//whatshapp
        chat = findViewById(R.id.whatshapp_icon);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Spotify");

        mainText = findViewById(R.id.mainText);
        database = FirebaseDatabase.getInstance();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        reference = FirebaseStorage.getInstance().getReference("Main_Activity_Card/Main_Image.png");
        scrollView = findViewById(R.id.scrollView);
//      buttonTest = findViewById(R.id.buttonTest);

        image = findViewById(R.id.imageView4);
        viewPager = findViewById(R.id.viewPager);

        setCurrentUID();

        checkAndSetProfileImage();

        initSpotifyData();
        spotifyLinksInitRecyclerView();

        DatabaseReference ref = database.getReference().child("Aarti");

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getApplicationContext());
        viewPager.setAdapter(adapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem()<2){
                    viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                }
                else{
                    viewPager.setCurrentItem(0);
                }
                handler.postDelayed(this::run,10000);
            }
        },10000);

        //checkOffset();

        CardView profile = findViewById(R.id.cardView3);
        profile.setOnClickListener(view -> profile());

        CardView setting = findViewById(R.id.cardView);
        setting.setOnClickListener(view -> setting());


//whatshapp
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://wa.me/+919155182211");

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
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add handler to show the advertisement dialog after 10 seconds
        Handler alertHandler = new Handler();
        alertHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showAdvertismentDialog();
            }
        }, 5000);

    }

    private void showAdvertismentDialog() {
        // Inflate the dialog layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_advertisment, null);

        // Build the dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();

        // Set dialog properties
        dialog.setCanceledOnTouchOutside(true);

        // Get dialog views and set actions
        CardView closeButton = dialogView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }

    public void profile(){
        Intent intent = new Intent(MainActivity.this, Profile.class);
        startActivity(intent);

        // for animation
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(50);
            }
        }
    }

    public void setting(){
        Intent intent = new Intent(MainActivity.this, settings.class);
        startActivity(intent);

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

    public void onBackPressed() {
        super.onBackPressed();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public void gotourl(String s){
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void setCurrentUID() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        try {
            personalUID = firebaseUser.getUid();
        }
        catch (Exception e){
            Log.d(TAG, "setCurrentUID: " + e.getMessage());
        }
    }

    public String UriToString(Uri uri){
        try {
            InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            Log.e("ImageUtils", "File not found: " + e.getMessage());
            return null;
        }
    }

    private boolean isWhatsappInstalled() {
        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        if (PackageChecker.isPackageInstalled(this, "com.whatsapp")){
            Toast.makeText(this, "yes", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void spotifyLinksInitRecyclerView(){
        aartiRecyclerView = findViewById(R.id.aartiRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        aartiRecyclerView.setLayoutManager(layoutManager);
        spotifyAdapter = new SpotifyLinkAdapter(dataList);
        aartiRecyclerView.setAdapter(spotifyAdapter);
        spotifyAdapter.notifyDataSetChanged();
    }

    public void initSpotifyData(){
        dataList = new ArrayList<>();

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("Spotify");

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds: snapshot.getChildren()){
                    dataList.add(new SpotifyLinkModelClass(ds.getKey(),ds.getValue().toString()));
                    spotifyAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkAndSetProfileImage(){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child("profileData").child(personalUID).child("profileimage.jpg");

        storageReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                String x = bitmapToString(bitmap);
                SharedPreferences sharedPreferences = getSharedPreferences("userData",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profilePicture",x);
                editor.commit();
            }
        });
    }

    public String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
