package com.abhijeet.ganpatiapp.adapters;

import static androidx.core.content.ContextCompat.getSystemService;

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
import com.abhijeet.ganpatiapp.activities.puja_list_view;
import com.abhijeet.ganpatiapp.modelclass.PujaListModelClass;
import com.google.android.material.divider.MaterialDivider;

import java.util.List;

public class PujaListAdapter extends RecyclerView.Adapter<PujaListAdapter.ViewHolder>{

    private List<PujaListModelClass> dataList;

    public PujaListAdapter(List<PujaListModelClass> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.puja_list_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = dataList.get(position).getName();
        holder.setData(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                vibrate(context, 50);

                Intent intent = new Intent(view.getContext(), puja_list_view.class);
                intent.putExtra("name",name);
                view.getContext().startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.textView21);

        }

        public void setData(String name) {
            Name.setText(name);
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
