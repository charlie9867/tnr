package com.seoul.cms.base;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.provider.Settings;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;



import com.seoul.cms.helper.JsonClient;
import com.seoul.cms.helper.PreferencesHelper;
import com.seoul.tnr.MainActivity;
import com.seoul.tnr.R;



public class BaseActivity extends AppCompatActivity {

    // 장치 고유 아이디
    public String androidId;
    public JsonClient client;
    public String nickname;
    public String my_gender;
    public String other_gender;
    public String app_uid;
    public String ma_code;



    Bundle savedInstanceState;
    View topActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = JsonClient.getInstance(this);

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        SharedPreferences preferences = PreferencesHelper.getInstance(this).getPreferences();
        nickname = preferences.getString("nickname", null);
        my_gender = preferences.getString("my_gender", null);
        other_gender = preferences.getString("other_gender", null);
        app_uid = preferences.getString("app_uid", null);
        ma_code = preferences.getString("ma_code", null);

    }

    // 탑 액션바 - 사이드바 포함
    public void setActionBarVisible() {
        setActionBarVisible(false);
    }

    // 탑 액션바 - 사이드바 포함
    public void setActionBarVisible(boolean isMain) {
        // 툴바 객체화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 툴바에 표시할 상단바 레이아웃을 처리함
        topActionBar = getLayoutInflater().inflate(R.layout.toolbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        toolbar.addView(topActionBar, params);


        ((ImageButton) topActionBar.findViewById(R.id.btActionBarLeft)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ((ImageButton) topActionBar.findViewById(R.id.btTopLogo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                intent.putExtra("IS_RELOAD", "Y");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        ((ImageButton) topActionBar.findViewById(R.id.btActionBarMy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BaseActivity.this, MainActivity.class));
            }
        });

    }




    // 탑 서브액션바 - 사이드바 미포함
    public void setSubActionBar(String title) {

        // 툴바 객체화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 툴바에 표시할 상단바 레이아웃을 처리함
        View mCustomView = getLayoutInflater().inflate(R.layout.toolbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        toolbar.addView(mCustomView, params);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}