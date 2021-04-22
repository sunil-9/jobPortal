package com.example.android.jobprovider.utils;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.android.jobprovider.utils.Constants.EMAIL;
import static com.example.android.jobprovider.utils.Constants.USERNAME;

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    String email,password;

    // shared pref mode
    int PRIVATE_MODE = 0;
    public static String pushRID = "0";
    // Shared preferences file name
    private static final String PREF_NAME = "GrandeeNotes-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String LOGIN_ID = "LOGIN";
    public static String Type = "image";

    public static Typeface scriptable;

    public static final String NIGHT_MODE = "NIGHT_MODE";
    private boolean isNightModeEnabled = false;
    private String filename;

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setLoginId(String id) {
        editor.putString(LOGIN_ID, id);
        editor.commit();
    }

    public String getLoginId() {
        return pref.getString(LOGIN_ID, "0");
    }
 public void setemail(String email) {
        editor.putString(EMAIL, email);
        editor.commit();
    }

    public String getemail() {
        return pref.getString(EMAIL, "0");
    }
 public void setusername(String username) {
        editor.putString(USERNAME, username);
        editor.commit();
    }

    public String getusername() {
        return pref.getString(USERNAME, "0");
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setBool(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBool(String key) {
        return pref.getBoolean(key, true);
    }

    public void setValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key) {
        return pref.getString(key, "0");
    }


}
