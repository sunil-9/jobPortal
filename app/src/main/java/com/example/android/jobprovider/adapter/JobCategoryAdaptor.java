package com.example.android.jobprovider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.model.JobApplyedDetails;
import com.example.android.jobprovider.model.JobAvailable;
import com.example.android.jobprovider.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import static com.example.android.jobprovider.utils.Constants.JOB_APPLIED_DETAILS;

public class JobCategoryAdaptor extends FirebaseRecyclerAdapter<
        JobAvailable, JobCategoryAdaptor.jobViewholder> {
    Context c;
    LinearLayout job_layout;
    String job_id, user_id, location, text_job_title, cat, designation, salary, website, phone, no_of_vacancy, qualification, skills, exp_date, experiences, post_date, isAvailable;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    public JobCategoryAdaptor(
            @NonNull FirebaseRecyclerOptions<JobAvailable> options, Context c) {
        super(options);
        this.c = c;
    }

    @Override
    protected void onBindViewHolder(@NonNull JobCategoryAdaptor.jobViewholder holder, int position, @NonNull JobAvailable model) {
        holder.date.setText(model.getExpDate());
        holder.tv_salary.setText(model.getSalary());
        holder.job_title.setText(model.getJobTitle());
        job_id = model.getJob_id();
        user_id = model.getProvider_id();
        location = model.getLocation();
        text_job_title = model.getJobTitle();
        cat = model.getCat();
        designation = model.getDesignation();
        salary = model.getSalary();
        website = model.getWebsite();
        phone = model.getPhone();
        no_of_vacancy = model.getNoOfVacancy();
        qualification = model.getQualification();
        skills = model.getSkills();
        exp_date = model.getExpDate();
        experiences = model.getExperiences();
        post_date = model.getPostDate();
        isAvailable = model.getIsAvailable();
    }
    @NonNull
    @Override
    public JobCategoryAdaptor.jobViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_item_data, parent, false);

        return new JobCategoryAdaptor.jobViewholder(view);
    }

    public class jobViewholder extends RecyclerView.ViewHolder {
        public TextView job_title, date, tv_salary;

        public jobViewholder(@NonNull View itemView) {
            super(itemView);
            job_layout = itemView.findViewById(R.id.job_layout);
            job_title = itemView.findViewById(R.id.job_title);
            date = itemView.findViewById(R.id.date);
            tv_salary = itemView.findViewById(R.id.salary);
            job_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(c, "clicked here", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder myDialog = new AlertDialog.Builder(c);
                    LayoutInflater inflater = LayoutInflater.from(c);
                    View myView = inflater.inflate(R.layout.show_job_details, null);
                    myDialog.setView(myView);
                    final AlertDialog dialog = myDialog.create();
                    final MaterialTextView txt_location = myView.findViewById(R.id.location);
                    final MaterialTextView txt_job_title = myView.findViewById(R.id.job_title);
                    final MaterialTextView txt_designation = myView.findViewById(R.id.designation);
                    final MaterialTextView txt_salary = myView.findViewById(R.id.salary);
                    final MaterialTextView txt_website = myView.findViewById(R.id.website);
                    final MaterialTextView txt_phone = myView.findViewById(R.id.phone);
                    final MaterialTextView txt_vacancy = myView.findViewById(R.id.vacancy);
                    final MaterialTextView txt_qualifications = myView.findViewById(R.id.qualifications);
                    final MaterialTextView txt_skills = myView.findViewById(R.id.skills);
                    final MaterialTextView txt_date = myView.findViewById(R.id.date);
                    final MaterialTextView txt_experience = myView.findViewById(R.id.experience);
                    final MaterialTextView txt_cat = myView.findViewById(R.id.category);
                    final Button applyJob = myView.findViewById(R.id.apply_job);

                    txt_location.setText(location);
                    txt_job_title.setText(text_job_title);
                    txt_designation.setText(designation);
                    txt_salary.setText(salary);
                    txt_website.setText(website);
                    txt_phone.setText(phone);
                    txt_vacancy.setText(no_of_vacancy);
                    txt_qualifications.setText(qualification);
                    txt_skills.setText(skills);
                    txt_date.setText(exp_date);
                    txt_experience.setText(experiences);
                    txt_cat.setText(cat);

                    dialog.show();

                    applyJob.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String seeker_id = FirebaseAuth.getInstance().getUid();
                            String today_date = DateFormat.getDateInstance().format(new Date());
                            databaseReference = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference(JOB_APPLIED_DETAILS);
                            String job_Applied_Id = databaseReference.push().getKey();
                            JobApplyedDetails jobApplyedDetails = new JobApplyedDetails(job_Applied_Id, job_id, user_id, seeker_id, today_date);
                            databaseReference.child(job_Applied_Id).setValue(jobApplyedDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(c, "applied successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(c, "Something went wrong, Please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
