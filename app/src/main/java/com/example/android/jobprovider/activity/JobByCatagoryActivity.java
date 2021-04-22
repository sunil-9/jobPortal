package com.example.android.jobprovider.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.JobCategoryAdaptor;
import com.example.android.jobprovider.model.JobAvailable;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.android.jobprovider.utils.Constants.JOB_AVAILABLE;

public class JobByCatagoryActivity extends AppCompatActivity {
    String cat_name;
    JobCategoryAdaptor adapter;
    private DatabaseReference mdatabase;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_by_catagory);
        getBunData();
        if(cat_name == null || cat_name.length() == 0) {
        }
        else{
//            Toast.makeText(this, ""+cat_name, Toast.LENGTH_SHORT).show();


            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(JOB_AVAILABLE);
            Query queries = ref.orderByChild("cat").equalTo(cat_name);
            recyclerView = findViewById(R.id.recycleView);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            FirebaseRecyclerOptions<JobAvailable> options
                    = new FirebaseRecyclerOptions.Builder<JobAvailable>()
                    .setQuery(queries, JobAvailable.class)
                    .build();

            adapter = new JobCategoryAdaptor(options, this);
            recyclerView.setAdapter(adapter);
        }
    }
    private void getBunData() {
        Bundle bundle = getIntent().getExtras();
        cat_name = bundle.getString("cat_name");

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