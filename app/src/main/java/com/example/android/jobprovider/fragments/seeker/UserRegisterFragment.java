package com.example.android.jobprovider.fragments.seeker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.android.jobprovider.model.SeekersInfo;
import com.example.android.jobprovider.utils.Constants;
import com.example.android.jobprovider.activity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Objects;

import com.example.android.jobprovider.R;


public class UserRegisterFragment extends Fragment {
    private MaterialEditText userName, emailAddress, password, mobile;
    private RadioGroup radioGroup;
    private Button registerBtn;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    public UserRegisterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_user_register, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        userName= view.findViewById(R.id.userName);
        emailAddress=view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        mobile= view.findViewById(R.id.number);
        radioGroup= view.findViewById(R.id.radioButton);
        registerBtn= view.findViewById(R.id.register);
        progressBar= view.findViewById(R.id.progressBar);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the values inserted in the field are extracted and saved in varaible String
                final String user_name= userName.getText().toString();
                final String email = emailAddress.getText().toString();
                final String txt_password=password.getText().toString();
                final String txt_mobile = mobile.getText().toString();
                int checkedId=radioGroup.getCheckedRadioButtonId();
                RadioButton selected_gender = radioGroup.findViewById(checkedId);
                if(selected_gender == null){
                    //if no gender is slected this toast is pops up
                    Toast.makeText(getActivity(), "Select gender please", Toast.LENGTH_SHORT).show();
                }
                else{
                    final String gender = selected_gender.getText().toString();
                    if(TextUtils.isEmpty(user_name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(txt_password)
                            || TextUtils.isEmpty(txt_mobile)){
                        //is anyone field is left blank toast pops up
                        Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        register(user_name, email, txt_password,txt_mobile, gender);
                    }

                }
            }
        });
        return  view;
    }
    private void register(final String user_name, final String email, final String txt_password, final String txt_mobile, final String gender) {
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser rUser= firebaseAuth.getCurrentUser();
                    String userId = rUser.getUid();
                    databaseReference = FirebaseDatabase.getInstance(Constants.DATABASE_URL).getReference("seekers").child(userId);

                    SeekersInfo userData =new SeekersInfo(userId,user_name ,email,"default",gender,txt_mobile,"default","default","not set","not set","not set","not set","not set");

                    databaseReference.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()) {
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}