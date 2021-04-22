package com.example.android.jobprovider.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.android.jobprovider.R;

public class PolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.privacypolicies.com/live/abcd6979-dcc3-4239-b1d9-5ca26e7dd28c");

        TextView tv_back=findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}