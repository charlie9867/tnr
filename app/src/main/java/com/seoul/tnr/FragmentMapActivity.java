package com.seoul.tnr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragmentMapActivity extends FragmentActivity {

    @BindView(R.id.btn1)
    Button btn1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_map);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn1)
    void onBtn1Clicked() {
        Toast.makeText(this, "버튼눌림", Toast.LENGTH_SHORT).show();
        Fragement1 fragment1 = new Fragement1();
        fragment1.setArguments(new Bundle());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHere, fragment1);
        fragmentTransaction.commit();
    }
}
