package com.abhijeet.ganpatiapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.abhijeet.ganpatiapp.R;
import com.abhijeet.ganpatiapp.activities.Aarti_view;
import com.abhijeet.ganpatiapp.activities.Kundali_entry;
import com.abhijeet.ganpatiapp.activities.MainActivity;
import com.abhijeet.ganpatiapp.activities.Puja_list;
import com.abhijeet.ganpatiapp.fragments.AartiFragment;
import com.abhijeet.ganpatiapp.fragments.KundaliFragment;
import com.abhijeet.ganpatiapp.fragments.PujaListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;


    public ViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AartiFragment();
        } else if (position == 1) {
            return new PujaListFragment();
        } else {
            return new KundaliFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.fragment_aarti, container, false);

        itemView.findViewById(R.id.fragmentText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "yay", Toast.LENGTH_SHORT).show();
            }
        });

        return super.instantiateItem(container, position);


    }
}
