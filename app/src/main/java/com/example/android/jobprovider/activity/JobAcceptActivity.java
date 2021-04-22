package com.example.android.jobprovider.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.model.JobAcceptedModel;
import com.example.android.jobprovider.model.SeekersInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.jobprovider.utils.Constants.CHILD_SEEKER;
import static com.example.android.jobprovider.utils.Constants.JOB_ACCEPTED;
import static com.example.android.jobprovider.utils.Constants.JOB_APPLIED_DETAILS;

public class JobAcceptActivity extends AppCompatActivity {
    private DatabaseReference ref;
    private DatabaseReference mDatabase;
    private ImageView can_image;
    FirebaseDatabase database;
    private TextView can_name, can_phone, can_email, can_location, can_cert, can_ach,can_dob,can_exp,can_skills;
    private String  seeker_id,status,job_id;
    private Button delete,invite;
    private Boolean isEdit=false;

    JobAcceptedModel jobAccept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_accept);
        setupUI();
        getBundleData();
        read_profile();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        can_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(SeekerProfileActivity.this, "image button clicked", Toast.LENGTH_SHORT).show();
            }
        });

            invite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder myDialog = new AlertDialog.Builder(JobAcceptActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(JobAcceptActivity.this);
                    View myView = inflater.inflate(R.layout.job_accept_input_item, null);
                    myDialog.setView(myView);
                    final AlertDialog dialog = myDialog.create();
                    final EditText et_time = myView.findViewById(R.id.et_time);

                    Button btn_confirm = myView.findViewById(R.id.btn_confirm);
                    dialog.show();
                    btn_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // we are use add value event listener method
                            // which is called with database reference.
                            ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    // inside the method of on Data change we are setting
                                    // our object class to our database reference.
                                    // data base reference will sends data to firebase.
                                    ref.setValue(jobAccept);

                                    // after adding this data we are showing toast message.
                                    Toast.makeText(JobAcceptActivity.this, "data added", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // if the data is not added or it is cancelled then
                                    // we are displaying a failure toast message.
                                    Toast.makeText(JobAcceptActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
                                }
                            });
                            Toast.makeText(JobAcceptActivity.this, "done "+seeker_id+" "+" "+job_id+" "+et_time.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query queryy = ref.child(JOB_APPLIED_DETAILS).orderByChild("job_id").equalTo(job_id);

                    queryy.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("TAG", "onCancelled", databaseError.toException());
                        }
                    });

                    Toast.makeText(JobAcceptActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            });
        }


    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            status = bundle.getString("applyed_id");
            seeker_id = bundle.getString("seeker_id");
            job_id = bundle.getString("applyed_id");
            isEdit = bundle.getBoolean("isEdit");
        }
    }

    private void setupUI() {
        seeker_id = FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        ref=database.getReference(JOB_ACCEPTED);
        can_name = findViewById(R.id.can_name);
        can_phone = findViewById(R.id.can_phone);
        can_email = findViewById(R.id.can_email);
        can_location = findViewById(R.id.can_location);
        can_cert = findViewById(R.id.can_certifications);
        can_ach = findViewById(R.id.can_achievements);
        can_image = findViewById(R.id.can_image);
        delete = findViewById(R.id.delete);
        can_dob = findViewById(R.id.can_dob);
        can_exp = findViewById(R.id.can_exp);
        can_skills = findViewById(R.id.can_skills);
        invite = findViewById(R.id.invite);
    }

    private void read_profile() {

            ref = database.getReference().child(CHILD_SEEKER).child(seeker_id);
// Attach a listener to read the data at our posts reference
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    SeekersInfo seekersInfo = dataSnapshot.getValue(SeekersInfo.class);
                    try {
                    can_name.setText(seekersInfo.getUsername());
                    can_phone.setText(seekersInfo.getMobile());
                    can_email.setText(seekersInfo.getEmail());
                    can_location.setText(seekersInfo.getLocation());
                    can_cert.setText(seekersInfo.getCertifications());
                    can_ach.setText(seekersInfo.getAchievements());
                    can_dob.setText(seekersInfo.getDob());
                    can_exp.setText(seekersInfo.getExperience());
                    can_skills.setText(seekersInfo.getSkills());
                    }catch (Exception e)
                    {
                        Toast.makeText(JobAcceptActivity.this, ""+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });


    }
}