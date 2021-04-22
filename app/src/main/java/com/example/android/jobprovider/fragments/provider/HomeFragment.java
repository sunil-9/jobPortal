package com.example.android.jobprovider.fragments.provider;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.JobProviderAdapter;
import com.example.android.jobprovider.model.JobAvailable;
import com.example.android.jobprovider.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class HomeFragment extends Fragment {
    JobProviderAdapter adapter;
    private DatabaseReference mdatabase;
    private FirebaseAuth mauth;
    private RecyclerView recyclerView;

    String location, job_title, designation, salary, website, phone, no_of_vacancy, qualification, skills, date, experiences, isAvailable, today_date;
    private String cat, uID;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser muser = mauth.getCurrentUser();
        uID = muser.getUid();
        mdatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference().child("JobAvailable");
        Query queries = mdatabase.orderByChild("provider_id").equalTo(muser.getUid());
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<JobAvailable> options
                = new FirebaseRecyclerOptions.Builder<JobAvailable>()
                .setQuery(queries, JobAvailable.class)
                .build();
        adapter = new JobProviderAdapter(options, getContext());
        recyclerView.setAdapter(adapter);

//        setUpRecyclerView();

        FloatingActionButton create = view.findViewById(R.id.float_create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
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
                final Spinner spinner = myView.findViewById(R.id.spinner);
                List<String> list = new ArrayList<String>();
                list.add("Bank");
                list.add("Teaching");
                list.add("Hotel");
                list.add("Vehicles");
                list.add("Office");
                list.add("Shop");
                list.add("Restaurant");
                list.add("Other");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

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
                                getActivity(),
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
                dialog.show();
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
                        date = txt_date.getText().toString().trim();

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
                        if (TextUtils.isEmpty(date)) {
                            txt_date.setError("Date is empty");
                            return;
                        }
                        if (TextUtils.isEmpty(experiences)) {
                            txt_experience.setError("Experiences is empty");
                            return;
                        }


                        String id = mdatabase.push().getKey();

                        isAvailable = "true";
                        today_date = DateFormat.getDateInstance().format(new Date());
                        JobAvailable data = new JobAvailable(id,uID, location, job_title,cat, designation, salary, website, phone, no_of_vacancy, qualification, skills, date, experiences, today_date, isAvailable);
                        mdatabase.child(id).setValue(data);
                        Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }

    }
}

