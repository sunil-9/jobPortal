package com.example.android.jobprovider.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.fragments.provider.SettingFragment;
import com.example.android.jobprovider.fragments.provider.HomeFragment;
import com.example.android.jobprovider.fragments.provider.JobsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProviderHome extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //make home fragement the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        //instance of bottomNavigation
        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);
    };
    //Listener nav bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.jobsFragment:
                    selectedFragment = new JobsFragment();
                    break;
                case R.id.settingsFragment:
                    selectedFragment = new SettingFragment();
                    break;
            }
            // fragment transaction
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment).commit();
            return true;


        }
    };
    //enter back button twice
    int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    long mBackPressed;
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else {

            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }


}