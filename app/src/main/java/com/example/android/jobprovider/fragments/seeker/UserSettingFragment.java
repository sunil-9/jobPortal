package com.example.android.jobprovider.fragments.seeker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.adapter.AcceptedJobAdapter;
import com.example.android.jobprovider.model.JobAcceptedModel;
import com.example.android.jobprovider.utils.Constants;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.content.ContentValues.TAG;
import static com.example.android.jobprovider.utils.Constants.JOB_ACCEPTED;
import static com.example.android.jobprovider.utils.Constants.JOB_APPLIED_DETAILS;


public class UserSettingFragment extends Fragment {

    View view;
    AcceptedJobAdapter adapter;
    private DatabaseReference mdatabase;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;

    public UserSettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_setting, container, false);
        mAuth =FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child(JOB_ACCEPTED).orderByChild("seeker_id").equalTo(mAuth.getUid());
       recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<JobAcceptedModel> options
                = new FirebaseRecyclerOptions.Builder<JobAcceptedModel>()
                .setQuery(query, JobAcceptedModel.class)
                .build();

        adapter = new AcceptedJobAdapter(options, getContext());
        recyclerView.setAdapter(adapter);
        return view;
    }
}