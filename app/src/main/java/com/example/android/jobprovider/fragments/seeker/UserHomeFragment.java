package com.example.android.jobprovider.fragments.seeker;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.android.jobprovider.R;
import com.example.android.jobprovider.activity.JobByCatagoryActivity;

public class UserHomeFragment extends Fragment {
    private CardView bank_view, teach_view, hotel_view, vehicle_view, office_view, shop_view, restaurant_view, other_view;
    String cat_name;
    View view;
    Bundle bundle;
    Intent intent;

    public UserHomeFragment() {
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
        view = inflater.inflate(R.layout.fragment_user_home, container, false);
        setupUI();
        intent = new Intent(getActivity(), JobByCatagoryActivity.class);

        // crating a bundle object
        bundle = new Bundle();

        bank_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Bank";
                openIntent();

            }
        });
        teach_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Teaching";
                openIntent();

            }
        });
        hotel_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Hotel";
                openIntent();


            }
        });
        vehicle_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Vehicles";
                openIntent();

            }
        });
        office_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Office";
                openIntent();

            }
        });
        shop_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Shop";
                openIntent();

            }
        });
        restaurant_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Restaurant";
                openIntent();

            }
        });
        other_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_name = "Other";
                openIntent();
            }
        });
        return view;
    }


    private void openIntent() {
        bundle.putString("cat_name", cat_name);
        intent.putExtras(bundle);
        Toast.makeText(getActivity(), "cat is " + cat_name, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    private void setupUI() {
        bank_view = view.findViewById(R.id.cardBank);
        teach_view = view.findViewById(R.id.cardTeaching);
        hotel_view = view.findViewById(R.id.cardHnS);
        vehicle_view = view.findViewById(R.id.cardVechicles);
        office_view = view.findViewById(R.id.cardOffice);
        shop_view = view.findViewById(R.id.cardShop);
        restaurant_view = view.findViewById(R.id.cardRestaurant);
        other_view = view.findViewById(R.id.cardOther);
    }
}
