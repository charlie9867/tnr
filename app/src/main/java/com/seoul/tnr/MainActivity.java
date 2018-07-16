package com.seoul.tnr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.logInBtn)
    void onLogInButtonClicked() {
        Intent intent = new Intent(this, FragmentMapActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.joinTv)
    void onJoinTvClicked() {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.findTv)
    void onFindTvClicked() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
