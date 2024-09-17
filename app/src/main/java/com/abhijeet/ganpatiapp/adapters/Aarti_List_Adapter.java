package com.abhijeet.ganpatiapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.activities.Aarti_view;
import com.abhijeet.ganpatiapp.modelclass.Aarti_List_Model_Class;
import java.util.List;
public class Aarti_List_Adapter extends RecyclerView.Adapter<Aarti_List_Adapter.ViewHolder> {

    private final List<Aarti_List_Model_Class> list;
    public Aarti_List_Adapter(List<Aarti_List_Model_Class> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Aarti_List_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aarti_list_item_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Aarti_List_Adapter.ViewHolder holder, int position) {

        String name = list.get(position).getName();
        holder.setData(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                // Vibrate for 50ms
                vibrate(context, 50);
                // Create an Intent to start the Aarti_view activity
                Intent intent = new Intent(context, Aarti_view.class);
                intent.putExtra("name", name);
                context.startActivity(intent);
                // For activity transition animation
                if (context instanceof Activity) {
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.textView21);
        }
        public void setData(String name) {
            nameView.setText(name);
        }
    }

    // Vibrate while selecting the Aarti
    public static void vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Deprecated in API 26 (Oreo) but still works for earlier versions
                vibrator.vibrate(milliseconds);
            }
        }
    }
}
