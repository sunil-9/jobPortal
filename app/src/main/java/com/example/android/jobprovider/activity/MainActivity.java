package com.example.android.jobprovider.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.utils.Constants;
import com.example.android.jobprovider.utils.PrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    private MaterialEditText email, password;
    private ProgressBar progressBar;
    private TextView forgetPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = findViewById(R.id.login);
        Button registerBtn= findViewById(R.id.register);
        forgetPassword =findViewById(R.id.forgetPass);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        firebaseAuth= FirebaseAuth.getInstance();
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgetPaswordActivity.class));

            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));

            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email= email.getText().toString();
                String txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(MainActivity.this, "All Fields Required",Toast.LENGTH_SHORT).show();
                }
                else{
                    login(txt_email, txt_password);
                }
            }
        });

    }

    private void login(String txt_email, String txt_password) {
        progressBar.setVisibility(VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String udi = firebaseAuth.getUid();
                    PrefManager prefManager =new PrefManager(MainActivity.this);
                    prefManager.setemail(txt_email);

                    DatabaseReference rootRef = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference();
                    DatabaseReference userNameRef = rootRef.child("provider").child(udi);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(!dataSnapshot.exists()) {
                                //create new user
                                Intent intent = new Intent(MainActivity.this, SeekerDashboardActivity.class);
                                Toast.makeText(MainActivity.this, "it is job seeker", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Intent intent = new Intent(MainActivity.this, ProviderHome.class);
                                Toast.makeText(MainActivity.this, "it is Job provider", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TAG", databaseError.getMessage()); //Don't ignore errors!
                        }
                    };
                    userNameRef.addListenerForSingleValueEvent(eventListener);
                } else {
                    progressBar.setVisibility(GONE);
                    Toast.makeText(MainActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }
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