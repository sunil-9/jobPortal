package com.example.android.jobprovider.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.WallpaperColors;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.model.SeekersInfo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

import static com.example.android.jobprovider.utils.Constants.CHILD_SEEKER;
import static com.example.android.jobprovider.utils.Constants.DATABASE_URL;

public class SeekerProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    final static int PICK_PDF_CODE = 2342;
    TextInputEditText u_city, u_name, u_skills, u_achievements, u_certifications, u_phone, u_experience, u_email;
    MaterialTextView textViewStatus, u_dob;
    ImageView date;
    ProgressBar progressBar;
    RadioGroup u_radioGroup;
    MaterialRadioButton male, female;
    FirebaseAuth mAuth;
    FirebaseUser user;
    StorageReference mStorageReference;
    DatabaseReference databaseReference;
    String seeker_id, email;
    private FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_profile_edit);

        setupUI();
        read_profile();
        findViewById(R.id.btn_saveprofile).setOnClickListener(this);
        findViewById(R.id.cv).setOnClickListener(this);
    }

    private void setupUI() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        seeker_id = mAuth.getUid();
        database = FirebaseDatabase.getInstance();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        textViewStatus = findViewById(R.id.textViewStatus);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        u_name = findViewById(R.id.et_name);
        u_radioGroup = (RadioGroup) findViewById(R.id.rg_sex);
        u_city = findViewById(R.id.et_city);
        u_phone = findViewById(R.id.et_phone);
        u_skills = findViewById(R.id.et_skills);
        u_achievements = findViewById(R.id.et_achievement);
        u_certifications = findViewById(R.id.et_certification);
        u_experience = findViewById(R.id.et_experience);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        male.setChecked(true);
        u_dob = findViewById(R.id.et_dob);
        u_email = findViewById(R.id.et_email);

        date = findViewById(R.id.date);
        findViewById(R.id.btn_saveprofile).setOnClickListener(this);
        findViewById(R.id.cv).setOnClickListener(this);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SeekerProfileEditActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        u_dob.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });
    }

    //////to upload user data

    private void SaveProfile() {
        user = mAuth.getCurrentUser();
        String name = u_name.getText().toString().trim().toUpperCase();
        RadioGroup rg = findViewById(R.id.rg_sex);
        String sex = ((MaterialRadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString().toUpperCase();
        String city = u_city.getText().toString().trim().toUpperCase();
        String phone = u_phone.getText().toString().trim().toUpperCase();
        String skills = u_skills.getText().toString().trim().toUpperCase();
        String achievements = u_achievements.getText().toString().trim().toUpperCase();
        String certifications = u_certifications.getText().toString().trim().toUpperCase();
        String experience = u_experience.getText().toString().trim().toUpperCase();
        email = u_email.getText().toString().trim().toUpperCase();
        String dob = u_dob.getText().toString();


        if (name.isEmpty()) {
            u_name.setError("Name Required");
            u_name.requestFocus();
            return;
        }
        if (city.isEmpty()) {
            u_city.setError("City Required");
            u_city.requestFocus();
            return;
        }
        if (skills.isEmpty()) {
            u_skills.setError("Skills Needed");
            u_skills.requestFocus();
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", user.getUid());
        map.put("username", name);
        map.put("email", email);
        map.put("location", city);
        map.put("gender", sex);
        map.put("mobile", phone);
        map.put("achievements", achievements);
        map.put("certifications", certifications);
        map.put("skills", skills);
        map.put("dob", dob);
        map.put("experience", experience);
        updateUserEmail();

        FirebaseDatabase.getInstance(DATABASE_URL).getReference().child(CHILD_SEEKER).child(user.getUid()).updateChildren(map);


        Toast.makeText(SeekerProfileEditActivity.this, "success", Toast.LENGTH_SHORT).show();
        Toast.makeText(SeekerProfileEditActivity.this, "Please Upload CV", Toast.LENGTH_SHORT).show();
    }

    private void updateUserEmail() {
        user.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SeekerProfileEditActivity.this, "successful updated email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    ////TO GET PDF FROM STORAGE
    private void getPdf() {
        ////intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //when user choose file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading file
                uploadPdfFile(data.getData());
            } else {
                Toast.makeText(this, "No File Choosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    ///THIS WILL UPLOAD PDF FILE
    private void uploadPdfFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);
        StorageReference sRef = mStorageReference.child("cv").child(System.currentTimeMillis() + ".pdf");

        UploadTask uploadTask = sRef.putFile(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("cv_link", downloadUri.toString());
                    FirebaseDatabase.getInstance(DATABASE_URL).getReference().child(CHILD_SEEKER).child(user.getUid()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onSuccess(Void aVoid) {
                                    textViewStatus.setText("Uploaded successfully");
                                    progressBar.setVisibility(View.GONE);

                                    Toast.makeText(SeekerProfileEditActivity.this, "successful database upload", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SeekerProfileEditActivity.this, "failed database upload " + e.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                textViewStatus.setText((int) progress + "% Uploading...");
            }
        });


    }

    private void read_profile() {
        databaseReference = database.getReference().child(CHILD_SEEKER).child(seeker_id);
// Attach a listener to read the data at our posts reference
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SeekersInfo seekersInfo = dataSnapshot.getValue(SeekersInfo.class);
try{
    u_name.setText(seekersInfo.getUsername());
    u_phone.setText(seekersInfo.getMobile());
    u_certifications.setText(seekersInfo.getCertifications());
    u_achievements.setText(seekersInfo.getAchievements());
    u_dob.setText(seekersInfo.getDob());
    u_experience.setText(seekersInfo.getExperience());
    u_skills.setText(seekersInfo.getSkills());
    u_email.setText(seekersInfo.getEmail());
    u_city.setText(seekersInfo.getLocation());
}catch (Exception e){

}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_saveprofile:
                SaveProfile();
                break;
            case R.id.cv:
                getPdf();
                break;
            default:
                break;
        }
    }
}
