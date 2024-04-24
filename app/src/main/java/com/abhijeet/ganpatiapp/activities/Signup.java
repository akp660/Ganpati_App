package com.abhijeet.ganpatiapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.EditText;

import com.abhijeet.ganpatiapp.R;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText name, email, phone, address, password;
    MaterialCardView signUpButton;

    CardView loginCard;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        phone = findViewById(R.id.editTextPhone);
        address = findViewById(R.id.editTextAddress);
        password = findViewById(R.id.editTextPassword);
        signUpButton = findViewById(R.id.signUpButton);
        loginCard = findViewById(R.id.loginInCard);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getName = name.getText().toString();
                String getEmail = email.getText().toString();
                String getPhone = phone.getText().toString();
                String getPassword = password.getText().toString();
                String getAddress = address.getText().toString();

                if ((!TextUtils.isEmpty(getName)) && (!TextUtils.isEmpty(getAddress)) && (!TextUtils.isEmpty(getPassword)) && (!TextUtils.isEmpty(getEmail)) && (!TextUtils.isEmpty(getPhone)) && (getPassword.length()>=6)){

                    firebaseAuth.createUserWithEmailAndPassword(getEmail,getPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                database.getReference().child("Users").child(firebaseAuth.getUid()).child("Name").setValue(getName);
                                database.getReference().child("Users").child(firebaseAuth.getUid()).child("Phone").setValue(getPhone);
                                database.getReference().child("Users").child(firebaseAuth.getUid()).child("Address").setValue(getAddress);
                                database.getReference().child("Users").child(firebaseAuth.getUid()).child("Email").setValue(getEmail);
                                Intent intent = new Intent(Signup.this, Login.class);
                                startActivity(intent);
                                finish();
                            }

                            else{
                                Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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


        loginCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                // for animation
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();

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


}