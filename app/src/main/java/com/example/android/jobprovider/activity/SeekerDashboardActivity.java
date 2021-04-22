package com.example.android.jobprovider.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.fragments.seeker.UserHomeFragment;
import com.example.android.jobprovider.fragments.seeker.UserJobFragment;
import com.example.android.jobprovider.fragments.seeker.UserSettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SeekerDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //this are the for drawers
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    TextView UserNameView;
    // drawer end

    private FirebaseAuth mAuth;
    private String user_id;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_dashboard);
        Toolbar toolbars = findViewById(R.id.taskbar_common);
        setSupportActionBar(toolbars);
        mAuth = FirebaseAuth.getInstance();
        user_id =mAuth.getUid();

        //for drawer instance are created
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        toggle=new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();




        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);
        //drawer section end
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //make home fragement the default fragment
        //this is for bottom navigation
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new UserHomeFragment()).commit();




        //instance of bottomNavigation
        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);

    };
    //Listener bottom navigation bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new UserHomeFragment();
                    break;
                case R.id.jobsFragment:
                    selectedFragment = new UserJobFragment();
                    break;
                case R.id.acceptedJob:
//                    Toast.makeText(SeekerDashboardActivity.this, "clicked here", Toast.LENGTH_SHORT).show();
                    selectedFragment = new UserSettingFragment();
                    break;

            }
            // fragment transaction
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment).commit();
            return true;


        }
    };


    //this helps the hamburger to react
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return  true;
    }
    //this help the drawer to select and item and  display accordingly
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_draw:
                onBackPressed();
                break;

            case R.id.userprofile_draw:
                Bundle bundle = new Bundle();
                bundle.putString("applyed_id", "");
                bundle.putString("seeker_id", user_id);
                Toast.makeText(this, ""+user_id, Toast.LENGTH_SHORT).show();
                bundle.putBoolean("isEdit",true);
                Intent intent = new Intent(SeekerDashboardActivity.this, SeekerProfileActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;

            case R.id.AboutUs_draw:
                Toast.makeText(this,"About us selected",Toast.LENGTH_LONG).show();
                break;
            case R.id.changepass_draw:
                startActivity(new Intent(SeekerDashboardActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.logout_draw:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to close this activity?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAuth.signOut();
                                startActivity(new Intent(SeekerDashboardActivity.this, SplashActivity.class));
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();


                break;
        }
        return true;
    }
    //when we open drawer and back button is pressed this code help us to go to the main page inside of closing our app

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}