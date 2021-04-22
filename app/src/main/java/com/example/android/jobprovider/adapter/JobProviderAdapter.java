package com.example.android.jobprovider.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.jobprovider.utils.Constants;
import com.example.android.jobprovider.R;
import com.example.android.jobprovider.model.JobAvailable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.android.jobprovider.utils.Constants.JOB_AVAILABLE;

public class JobProviderAdapter extends FirebaseRecyclerAdapter<
        JobAvailable, JobProviderAdapter.jobViewholder> {
    Context c;
    String jobid,uID,new_cat;
    private FirebaseAuth mauth;
    private DatabaseReference mdatabase;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String location, job_title,cat,dead_line, designation, salary, website, phone, no_of_vacancy, qualification, skills,  experiences, isAvailable, today_date;

    public JobProviderAdapter(
            @NonNull FirebaseRecyclerOptions<JobAvailable> options, Context c)
    {
        super(options);
        this.c =c;
    }

    @Override
    protected void onBindViewHolder(@NonNull jobViewholder holder, int position, @NonNull JobAvailable model) {
        holder.tv_dead_line.setText(model.getExpDate());
        holder.salary.setText(model.getSalary());
        holder.job_title.setText(model.getJobTitle());
        jobid =model.getJob_id();
        job_title =model.getJobTitle();
        location=model.getLocation();
        designation=model.getDesignation();
        salary=model.getSalary();
        website=model.getWebsite();
        phone=model.getPhone();
        no_of_vacancy=model.getNoOfVacancy();
        qualification=model.getQualification();
        skills=model.getSkills();
        dead_line=model.getExpDate();
        experiences=model.getExperiences();
        new_cat = model.getCat();

    }

    @NonNull
    @Override
    public jobViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_item_data, parent, false);
        return new JobProviderAdapter.jobViewholder(view);
    }

    public class jobViewholder extends RecyclerView.ViewHolder {
       public MaterialTextView job_title,tv_dead_line,salary;

        public jobViewholder(@NonNull View itemView) {
            super(itemView);
            job_title = itemView.findViewById(R.id.job_title);
            tv_dead_line = itemView.findViewById(R.id.date);
            salary = itemView.findViewById(R.id.salary);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    update_date();
                }
            });

        }
    }
    public void update_date() {
        mauth = FirebaseAuth.getInstance();
        FirebaseUser muser = mauth.getCurrentUser();
        uID = muser.getUid();
        mdatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference().child(JOB_AVAILABLE);
        AlertDialog.Builder myDialog = new AlertDialog.Builder(c);
        LayoutInflater inflater = LayoutInflater.from(c);
        View myView = inflater.inflate(R.layout.update_dialog, null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        Button btn_upd = myView.findViewById(R.id.btn_update);
        Button btn_deleteupd = myView.findViewById(R.id.btn_delete_from_update);

        dialog.show();

        btn_deleteupd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdatabase.child(jobid).removeValue();
                dialog.dismiss();
            }
        });
        btn_upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(c, "Data Updated "+jobid, Toast.LENGTH_SHORT).show();
                dialog.dismiss();


                AlertDialog.Builder myDialog = new AlertDialog.Builder(c);

                LayoutInflater inflater = LayoutInflater.from(c);
                View myView = inflater.inflate(R.layout.custom_input, null);
                myDialog.setView(myView);
                final AlertDialog dialog = myDialog.create();
                final TextInputEditText txt_location = myView.findViewById(R.id.location);
                final TextInputEditText txt_job_title = myView.findViewById(R.id.job_title);
                final TextInputEditText txt_designation = myView.findViewById(R.id.designation);
                final TextInputEditText txt_salary = myView.findViewById(R.id.salary);
                final TextInputEditText txt_website = myView.findViewById(R.id.website);
                final TextInputEditText txt_phone = myView.findViewById(R.id.phone);
                final TextInputEditText txt_vacancy = myView.findViewById(R.id.vacancy);
                final TextInputEditText txt_qualifications = myView.findViewById(R.id.qualifications);
                final TextInputEditText txt_skills = myView.findViewById(R.id.skills);
                final MaterialTextView txt_date = myView.findViewById(R.id.date);
                final TextInputEditText txt_experience = myView.findViewById(R.id.experience);
                //for spinner
                final AppCompatSpinner spinner = myView.findViewById(R.id.spinner);
                List<String> list = new ArrayList<String>();
                list.add("Bank");
                list.add("Teaching");
                list.add("Hotel");
                list.add("Vehicles");
                list.add("Office");
                list.add("Shop");
                list.add("Restaurant");
                list.add("Other");

//                INFO: setting default spinner value
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                if (new_cat != null) {
                    int spinnerPosition = adapter.getPosition(new_cat);
                    spinner.setSelection(spinnerPosition);
                }

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                cat = list.get(0);
                                break;
                            case 1:
                                cat = list.get(1);
                                break;
                            case 2:
                                cat = list.get(2);
                                break;
                            case 3:
                                cat = list.get(3);
                                break;
                            case 4:
                                cat = list.get(4);
                                break;
                            case 5:
                                cat = list.get(5);
                                break;
                            case 6:
                                cat = list.get(6);
                                break;
                            case 7:
                                cat = list.get(7);
                                break;

                            default:
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });

                txt_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(
                                c,
                                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                                mDateSetListener,
                                year, month, day);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });
                mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String date = month + "/" + day + "/" + year;
                        txt_date.setText(date);
                    }
                };
                Button btn_save = myView.findViewById(R.id.btn_save);
                btn_save.setText("Update");
                dialog.show();
                txt_location.setText(location);
                txt_job_title.setText(job_title);
                txt_designation.setText(designation);
                txt_salary.setText(salary);
                txt_website.setText(website);
                txt_phone.setText(phone);
                txt_vacancy.setText(no_of_vacancy);
                txt_qualifications.setText(qualification);
                txt_skills.setText(skills);
                txt_date.setText(dead_line);
                txt_experience.setText(experiences);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        location = txt_location.getText().toString().trim();
                        job_title = txt_job_title.getText().toString().trim();
                        designation = txt_designation.getText().toString().trim();
                        salary = txt_salary.getText().toString().trim();
                        website = txt_website.getText().toString().trim();
                        phone = txt_phone.getText().toString().trim();
                        no_of_vacancy = txt_vacancy.getText().toString().trim();
                        qualification = txt_qualifications.getText().toString().trim();
                        skills = txt_skills.getText().toString().trim();
                        dead_line = txt_date.getText().toString().trim();
                        experiences = txt_experience.getText().toString().trim();
                        if (TextUtils.isEmpty(location)) {
                            txt_location.setError("Location is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(job_title)) {
                            txt_job_title.setError("Job Title is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(designation)) {
                            txt_designation.setError("Designation is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(salary)) {
                            txt_salary.setError("Salary is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(website)) {
                            txt_website.setError("Website is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(phone)) {
                            txt_phone.setError("Phone is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(no_of_vacancy)) {
                            txt_vacancy.setError("No of vacancy is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(qualification)) {
                            txt_qualifications.setError("Qualification is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(skills)) {
                            txt_skills.setError("Skills is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(dead_line)) {
                            txt_date.setError("Date is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(experiences)) {
                            txt_experience.setError("Experiences is empty");
                            return;
                        }
                        mdatabase.child(jobid).removeValue();
                        isAvailable = "true";
                        today_date = DateFormat.getDateInstance().format(new Date());
                        JobAvailable data = new JobAvailable(jobid,uID,location, job_title,  cat, designation, salary, website, phone, no_of_vacancy, qualification, skills, dead_line, experiences, today_date, isAvailable);
                        mdatabase.child(jobid).setValue(data);
                        Toast.makeText(c, "Data updated", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });
    }

}
