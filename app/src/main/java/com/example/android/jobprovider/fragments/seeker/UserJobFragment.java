package com.example.android.jobprovider.fragments.seeker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.jobprovider.utils.Constants;
import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.JobSeeerAdapter;
import com.example.android.jobprovider.model.JobAvailable;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.android.jobprovider.utils.Constants.JOB_AVAILABLE;

public class UserJobFragment extends Fragment {
    View view;
    JobSeeerAdapter adapter;
    private DatabaseReference mdatabase;
    private RecyclerView recyclerView;

    public UserJobFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_job, container, false);
        mdatabase = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference().child(JOB_AVAILABLE);
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        FirebaseRecyclerOptions<JobAvailable> options
                = new FirebaseRecyclerOptions.Builder<JobAvailable>()
                .setQuery(mdatabase, JobAvailable.class)
                .build();

        adapter = new JobSeeerAdapter(options, getContext());
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