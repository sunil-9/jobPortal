package com.example.android.jobprovider.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.activity.SeekerProfileActivity;
import com.example.android.jobprovider.model.JobAcceptedModel;
import com.example.android.jobprovider.model.JobAvailable;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.jobprovider.utils.Constants.DATABASE_URL;
import static com.example.android.jobprovider.utils.Constants.JOB_AVAILABLE;

public class AcceptedJobAdapter extends FirebaseRecyclerAdapter<JobAcceptedModel, AcceptedJobAdapter.jobViewholder> {

        private static final String TAG = "";
        Context c;
        String job_id, seeker_id, provider_id, status;

        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;

        public AcceptedJobAdapter(
                @NonNull FirebaseRecyclerOptions<JobAcceptedModel> options, Context c) {
                super(options);
                this.c = c;
        }

        @Override
        protected void onBindViewHolder(@NonNull AcceptedJobAdapter.jobViewholder holder, int position, @NonNull JobAcceptedModel model) {
                job_id=model.getJob_id();
                seeker_id=model.getSeeker_id();
                firebaseAuth =FirebaseAuth.getInstance();
                Toast.makeText(c, ""+job_id, Toast.LENGTH_SHORT).show();
        databaseReference  = FirebaseDatabase.getInstance(DATABASE_URL).getReference().child(JOB_AVAILABLE).child(job_id);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                try{
                                                        JobAvailable jobAvailable = dataSnapshot.getValue(JobAvailable.class);
                                                        holder.tv_seeker_id.setText(model.getMsg());
//                                                        Log.e("TAG", "onBindViewHolder: username is  is  "+seekersInfo.getUsername() );
                                                }catch (Exception e){
                                                        Toast.makeText(c, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("The read failed: " + databaseError.getCode());
                                        }
                                });
                }
        @NonNull
        @Override
        public AcceptedJobAdapter.jobViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view
                        = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.candidate_list_item, parent, false);

                return new AcceptedJobAdapter.jobViewholder(view);
        }

        public class jobViewholder extends RecyclerView.ViewHolder {
                public TextView tv_job_id, tv_seeker_id, tv_applied_date;
                public RelativeLayout rl_parent_id;

                public jobViewholder(@NonNull View itemView) {
                        super(itemView);
                        tv_job_id = itemView.findViewById(R.id.job_title);
                        tv_seeker_id = itemView.findViewById(R.id.seeker_name);
                        tv_applied_date = itemView.findViewById(R.id.applied_date);
                        rl_parent_id = itemView.findViewById(R.id.rl_parent_id);
                        itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                        Bundle bundle = new Bundle();
                                        bundle.putString("seeker_id", seeker_id);
                                        bundle.putString("status", status);
                                        bundle.putString("applyed_id", job_id);
                                        bundle.putBoolean("isEdit", false);
                                        Intent intent = new Intent(c, SeekerProfileActivity.class);
                                        intent.putExtras(bundle);
                                        c.startActivity(intent);
                                }
                        });

                }
        }
}