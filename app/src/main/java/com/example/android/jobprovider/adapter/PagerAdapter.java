package com.example.android.jobprovider.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android.jobprovider.fragments.provider.CompanyRegisterFragment;
import com.example.android.jobprovider.fragments.seeker.UserRegisterFragment;

public class PagerAdapter extends FragmentStateAdapter {
    private final int get_item_count;

    public PagerAdapter(@NonNull FragmentActivity fm, int get_item_count) {
        super(fm);
        this.get_item_count = get_item_count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
                return new UserRegisterFragment();
            case 1:
                return new CompanyRegisterFragment();
            default:
                return null;
        }
    }
    @Override
    public int getItemCount() {
        return get_item_count;
    }
}
