package com.seoul.tnr;


import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        Fragment fr = new ListMapFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.add(R.id.listFg, fr).commit();

    }

    @OnClick(R.id.listMapBt)
    void onListMapBtClicked(){
        Fragment fr = new ListMapFragment();
        displayFragmentWithArg(fr);
    }

    @OnClick(R.id.listOrderBt)
    void onListOrderBt(){
        Fragment fr = new ListOrderFragment();
        displayFragmentWithArg(fr);
    }

    public void displayFragmentWithArg(Fragment mFragment) {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.listFg, mFragment).commit();
    }

    @OnClick(R.id.addFB)
    void onAddFb(){

        Toast.makeText(this, "common", Toast.LENGTH_SHORT).show();

    }




}
