package com.abhijeet.ganpatiapp.bottomsheets;

import static android.app.Activity.RESULT_OK;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.abhijeet.ganpatiapp.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class pictureFragment extends BottomSheetDialogFragment {

    ImageView galleryIcon, cameraIcon;

    int SELECT_PICTURE = 200;
    int CAMERA_REQ_CODE = 100;

    byte[] image;
    String images = "";

    transferPicture instance;

    String personalUID="";

    public pictureFragment(transferPicture picture) {
        this.instance=picture;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cameraIcon = view.findViewById(R.id.imageView9);
        galleryIcon = view.findViewById(R.id.imageView10);

        setCurrentUID();


        cameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQ_CODE);
            }
        });


        galleryIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQ_CODE) {
                Log.d("running", "onActivityResult: camera ran");
                Bitmap img = (Bitmap) (data.getExtras().get("data"));


                image = convertBitmapToByteArray(img);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    images = Base64.getEncoder().encodeToString(image);
                }
                editor.putString("profilePicture", images);
                editor.commit();
                if (instance!=null){
                    instance.transferImage(images);
                }

                else{
                    Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();

                }
                uploadBitmapImage(img);
//                uploadImageToFirebase(uri);
                dismiss();
            }

            else if (requestCode==SELECT_PICTURE){
                Uri imageUri = data.getData();
                image = convertImageToByteArray(imageUri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    images = Base64.getEncoder().encodeToString(image);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profilePicture", images);
                    editor.commit();
                    if (instance!=null){
                        instance.transferImage(images);
                    }
                    else {
                        Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                    }
                    uploadUriImage(imageUri);
                    dismiss();
                }
            }
        }
    }

    public byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // You can change the format and quality as needed
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private byte[] convertImageToByteArray(Uri uri) {
        try {
            // Load the image from URI
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);

            // Compress the image to JPEG format
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

            // Get the binary data from ByteArrayOutputStream
            byte[] imageData = byteArrayOutputStream.toByteArray();

            // Close the ByteArrayOutputStream
            byteArrayOutputStream.close();

            return imageData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface transferPicture{
        void transferImage(String image);
    }

//    public void uploadImageToFirebase(Uri uri){
//        FirebaseStorage storageReference = FirebaseStorage.getInstance();
//
//        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void uploadBitmapImage(Bitmap bitmap){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileData").child(personalUID).child("profileimage.jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReference.putBytes(data);
    }

    public void uploadUriImage(Uri uri){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileData").child(personalUID).child("profileimage.jpg");
        UploadTask uploadTask = storageReference.putFile(uri);
    }


}