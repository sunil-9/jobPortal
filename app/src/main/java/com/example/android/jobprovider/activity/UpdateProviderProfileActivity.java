package com.example.android.jobprovider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.model.ProviderInfo;
import com.example.android.jobprovider.model.SeekersInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;

import static com.example.android.jobprovider.utils.Constants.CHILD_PROVIDER;
import static com.example.android.jobprovider.utils.Constants.CHILD_SEEKER;
import static com.example.android.jobprovider.utils.Constants.DATABASE_URL;

public class UpdateProviderProfileActivity extends AppCompatActivity {
    final static int image = 2342;
    TextInputEditText u_location, u_company, u_email;
    Button btn_update;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseUser user;

    StorageReference mStorageReference;
    DatabaseReference databaseReference;
    String provider_id, email,location,company;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_provider_profile);
        setupUI();
        read_profile();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveProfile();
            }
        });


    }

    private void setupUI() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        provider_id = mAuth.getUid();
        database = FirebaseDatabase.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressBar = (ProgressBar) findViewById(R.id.progress);
        u_email = findViewById(R.id.et_email);
        u_location = findViewById(R.id.et_location);
        u_company = findViewById(R.id.et_company);
        btn_update =findViewById(R.id.btn_update);

    }
    private void read_profile() {
        databaseReference = database.getReference().child(CHILD_PROVIDER).child(provider_id);
// Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProviderInfo seekersInfo = dataSnapshot.getValue(ProviderInfo.class);
                try{

                    u_email.setText(seekersInfo.getEmail());
                    u_company.setText(seekersInfo.getCompany_Name());
                    u_location.setText(seekersInfo.getLocation());
                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void SaveProfile() {
        user = mAuth.getCurrentUser();
        email = u_email.getText().toString().trim().toUpperCase();
        location = u_location.getText().toString().trim().toUpperCase();
        company = u_company.getText().toString().trim().toUpperCase();



        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user.getUid());
        map.put("email", email);
        map.put("location", location);
        map.put("company_Name", company);

        updateUserEmail();
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();

        FirebaseDatabase.getInstance(DATABASE_URL).getReference().child(CHILD_PROVIDER).child(user.getUid()).updateChildren(map);
    }
    private void updateUserEmail() {
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(this, "successful updated email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}