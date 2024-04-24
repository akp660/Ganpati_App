package com.abhijeet.ganpatiapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.modelclass.Puja_List_View_model_Class;

import java.util.List;

public class PujaListViewAdapter extends RecyclerView.Adapter<PujaListViewAdapter.ViewHolder> {


    private List<Puja_List_View_model_Class> dataList;

    public PujaListViewAdapter(List<Puja_List_View_model_Class> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PujaListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.puja_list_view_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PujaListViewAdapter.ViewHolder holder, int position) {

        String name = dataList.get(position).getName();
        String data = dataList.get(position).getData();

        holder.setData(name,data);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;
        private TextView Data;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.textView21);
            Data = itemView.findViewById(R.id.textView26);
        }

        public void setData(String name, String data){
            Name.setText(name);
            Data.setText(data);
        }
    }
}
