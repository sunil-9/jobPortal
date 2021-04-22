package com.example.android.jobprovider.fragments.provider;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.android.jobprovider.BuildConfig;
import com.example.android.jobprovider.R;
import com.example.android.jobprovider.activity.AboutUsActivity;
import com.example.android.jobprovider.activity.PolicyActivity;
import com.example.android.jobprovider.activity.SplashActivity;
import com.example.android.jobprovider.activity.UpdateProviderProfileActivity;
import com.google.firebase.auth.FirebaseAuth;


public class SettingFragment extends Fragment {
    SwitchCompat switch_push, switch_theme;
    TextView txt_profile, txt_my_download_book, txt_about_us, txt_share_app, txt_rate_app, txt_login, txt_privacy_policy, txt_my_downloaded_book,txt_my_notifications;
    String currentLanguage = "en", currentLang;


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root=inflater.inflate(R.layout.fragment_setting, container, false);
        txt_profile = (TextView) root.findViewById(R.id.txt_profile);
        txt_about_us = root.findViewById(R.id.txt_about_us);
        txt_login = (TextView) root.findViewById(R.id.txt_login);

        txt_share_app = (TextView) root.findViewById(R.id.txt_share_app);
        txt_rate_app = (TextView) root.findViewById(R.id.txt_rate_app);
        txt_privacy_policy = root.findViewById(R.id.txt_privacy_policy);
        txt_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), PolicyActivity.class));
            }
        });
        txt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateProviderProfileActivity.class));
            }
        });
        txt_about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
            }
        });

        txt_share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "" + getResources().getString(R.string.app_name));
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });

        txt_rate_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getActivity().getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (prefManager.getLoginId().equalsIgnoreCase("0")) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                } else {
                    logout();
//                }
            }
        });


        return root;
    }
    public void logout() {
        new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppTheme))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth mauth = FirebaseAuth.getInstance();
                        mauth.signOut();
                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}