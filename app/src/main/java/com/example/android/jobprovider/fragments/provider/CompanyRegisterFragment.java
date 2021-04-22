package com.example.android.jobprovider.fragments.provider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.jobprovider.model.ProviderInfo;
import com.example.android.jobprovider.utils.Constants;
import com.example.android.jobprovider.activity.MainActivity;
import com.example.android.jobprovider.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;

public class CompanyRegisterFragment extends Fragment {
    private MaterialEditText userName, emailAddress, password, mobile, address;

    private Button registerBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;


    public CompanyRegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_company_register, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        userName = view.findViewById(R.id.userName);
        emailAddress = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        mobile = view.findViewById(R.id.number);
        address = view.findViewById(R.id.location);
        registerBtn = view.findViewById(R.id.register);
        progressBar = view.findViewById(R.id.progressBar);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the values inserted in the field are extracted and saved in varaible String
                final String user_name = userName.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password = password.getText().toString();
                final String txt_mobile = mobile.getText().toString();
                final String txt_location = address.getText().toString();
                if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(txt_password)
                        || TextUtils.isEmpty(txt_mobile)) {
                    //is anyone field is left blank toast pops up
                    Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    register(user_name, email, txt_password, txt_mobile, txt_location);
                }
            }

        });

        return view;
    }

    private void register(final String user_name, final String email, final String txt_password, final String txt_mobile, final String location) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser rUser = firebaseAuth.getCurrentUser();
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference("provider").child(userId);
                    ProviderInfo providerInfo = new ProviderInfo(userId,user_name,email,txt_mobile,location,"Default");
                    databaseReference.setValue(providerInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}