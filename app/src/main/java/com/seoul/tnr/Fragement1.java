package com.seoul.tnr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;

public class Fragement1 extends Fragment {

    private NMapContext mMapContext;
    private NMapLocationManager nMapLocationManager;
    private static final String CLIENT_ID = "6vmap7xfvr";// 애플리케이션 클라이언트 아이디 값
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_fragement1, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = getView().findViewById(R.id.mapView);
        mapView.setNcpClientId(CLIENT_ID);// 클라이언트 아이디 설정
        mMapContext.setupMapView(mapView);
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();

    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }
}
