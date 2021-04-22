package com.example.android.jobprovider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.PagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RegisterActivity extends AppCompatActivity {

    ViewPager simpleViewPager;
    Toolbar toolbar;
    private static final int NUM_PAGES = 2;
    private String[] titles = new String[]{ "As A Job Seeker","As A Job Provider"};
    TabLayout tabLayout;
    TabItem tab_course, tab_oldquestion, tab_syllabus;
    ViewPager2 viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //code go back to login page from the create new account activity
        toolbar = findViewById(R.id.signup_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.pager);
        pagerAdapter = new PagerAdapter(this, NUM_PAGES);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(titles[position])).attach();
    }
}



