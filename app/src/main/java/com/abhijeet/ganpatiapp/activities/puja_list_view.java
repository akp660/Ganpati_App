package com.abhijeet.ganpatiapp.activities;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.adapters.PujaListViewAdapter;
import com.abhijeet.ganpatiapp.modelclass.Puja_List_View_model_Class;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class puja_list_view extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    TextView title;
    List<Puja_List_View_model_Class> list;
    RecyclerView puja_list_recycler_view;
    LinearLayoutManager layoutManager;
    PujaListViewAdapter adapter;
    CardView sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.puja_list_view);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.puja_view_recycler_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        sendBtn = findViewById(R.id.order);
        list = new ArrayList<>();
        title = findViewById(R.id.textView29);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        title.setText(name);

        database = FirebaseDatabase.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Puja").child(name);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    String x = ds.getKey();
                    String y = ds.getValue().toString();
                    list.add(new Puja_List_View_model_Class(x,y));
                }

                initRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = title.getText().toString();

                String messagestr = "ORDER FOR PUJAN SAMAGRIH\n\n" + input; // Using the extracted input text

                if (!input.isEmpty()) {
                    if (iswhatsappInstalled()) {
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+919155182211&text=" + messagestr));
                        startActivity(i);
                    } else {
                        Toast.makeText(puja_list_view.this, "Whatsapp is not installed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(puja_list_view.this, "All fields are required", Toast.LENGTH_LONG).show();
                }
                triggerVibration();
            }
        });
    }


    private boolean iswhatsappInstalled() {
        PackageManager packageManager = getPackageManager();
        boolean whatsappInstalled;

        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            whatsappInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            whatsappInstalled = false;
        }
        // Toast.makeText(this, String.valueOf(whatsappInstalled), Toast.LENGTH_SHORT).show();
        return whatsappInstalled;
    }

    public void initRecyclerView() {
        puja_list_recycler_view = findViewById(R.id.puja_view_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        puja_list_recycler_view.setLayoutManager(layoutManager);
        adapter = new PujaListViewAdapter(list);
        puja_list_recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // Override the onBackPressed to handle custom back press logic
    @Override
    public void onBackPressed() {
        // Handle the back press like you would in the back button click
        super.onBackPressed();
        Intent intent = new Intent(puja_list_view.this, Puja_list.class);
        startActivity(intent);

        // Trigger the slide-in animation
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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