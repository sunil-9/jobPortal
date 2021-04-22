package com.example.android.jobprovider.fragments.provider;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.CandidateDetailsAdaptor;
import com.example.android.jobprovider.model.JobApplyedDetails;
import com.example.android.jobprovider.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.example.android.jobprovider.utils.Constants.JOB_APPLIED_DETAILS;

public class JobsFragment extends Fragment {
    View view;
    CandidateDetailsAdaptor adapter;
    private DatabaseReference mdatabase;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;
    public JobsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jobs, container, false);
        mAuth =FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference().child(JOB_APPLIED_DETAILS).orderByChild("provider_id").equalTo(mAuth.getUid());
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<JobApplyedDetails> options
                = new FirebaseRecyclerOptions.Builder<JobApplyedDetails>()
                .setQuery(query, JobApplyedDetails.class)
                .build();

        adapter = new CandidateDetailsAdaptor(options, getContext());
        recyclerView.setAdapter(adapter);
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