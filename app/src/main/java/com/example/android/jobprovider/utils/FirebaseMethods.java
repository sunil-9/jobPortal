package com.example.android.jobprovider.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.android.jobprovider.utils.Constants.JOB_APPLIED_DETAILS;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //Firebase
    private FirebaseAuth mAuth;
    private Context mContext;
    private String userID;
    private String profile_picture;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;


    public FirebaseMethods(Context context){
        mContext = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid().toString();
        }
    }

    public void setUserID(String id){
        userID= id;
    }

    public String getUserID(){
        return userID;
    }


    /**
     * Register a new email and password to Firebase auth
     * @param email
     * @param password
     */
    public void createAccount(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authenticated failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else if(task.isSuccessful()){
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }
                    }
                });

    }


    /**
     * @param user_id
     * @param currentLocation
     * @param destination
     * @param dateOfJourney
     * @param seatsAvailable
     * @param licencePlate
     * @param currentlongitude
     * @param currentlatitude
     * @param sameGender
     * @param luggageAllowance
     * @param car
     * @param pickupTime
     * @param extraTime
     */
    public void offerRide(String user_id, String username, String currentLocation, String destination, String dateOfJourney,
                          int seatsAvailable, String licencePlate, double currentlongitude, double currentlatitude, boolean sameGender, int luggageAllowance, String car,
                          String pickupTime, int extraTime, String profile_photo, int cost, int completeRides, int userRating, String duration, String pickupLocation){

//        String rideKey = mFirebaseDatabase.getReference().push().getKey();
//
//
//        OfferRide offerRide = new OfferRide(rideKey, user_id, username,destination, currentLocation, dateOfJourney, seatsAvailable, licencePlate,
//                currentlongitude, currentlatitude, sameGender, luggageAllowance, car, pickupTime, extraTime, profile_photo, cost,
//                completeRides, userRating, duration, pickupLocation);
//
//        myRef.child(mContext.getString(R.string.dbName_availableRide))
//                .child(rideKey)
//                .setValue(offerRide);
    }


    /**
     * Deletes current user ride from database
     * @param rideID
     */
    public void deleteRide(String rideID){

        myRef.child("availableRide")
                .child(rideID)
                .removeValue();

    }




    public String checkAppliedJob(final String provider_id){
        myRef.child(JOB_APPLIED_DETAILS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                            if (dataSnapshot2.getValue().equals(provider_id)){

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }


}
