package com.example.airpark.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.airpark.fragments.PastBookingsFragment;
import com.example.airpark.fragments.UpcomingBookingsFragment;


public class MyBookingsAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;

    public MyBookingsAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UpcomingBookingsFragment upcomingFragment = new UpcomingBookingsFragment();
                return upcomingFragment;
            case 1:
                PastBookingsFragment pastFragment = new PastBookingsFragment();
                return pastFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
