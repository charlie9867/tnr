package com.seoul.tnr;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.nhn.android.maps.NMapCompassManager;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.maps.overlay.NMapPOIitem;
import com.nhn.android.mapviewer.overlay.NMapCalloutCustomOverlay;
import com.nhn.android.mapviewer.overlay.NMapCalloutOverlay;
import com.nhn.android.mapviewer.overlay.NMapMyLocationOverlay;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;
import com.seoul.cms.helper.CustomDialog;
import com.seoul.cms.map.NMapCalloutCustomOldOverlay;
import com.seoul.cms.map.NMapCalloutCustomOverlayView;
import com.seoul.cms.map.NMapFragment;
import com.seoul.cms.map.NMapPOIflagType;
import com.seoul.cms.map.NMapViewerResourceProvider;


public class ListMapFragment extends NMapFragment implements NMapView.OnMapStateChangeListener, NMapPOIdataOverlay.OnStateChangeListener {

    private Context context;

    private NMapView mapView;
    private NMapContext mMapContext;
    private static final String CLIENT_ID = "6vmap7xfvr";// 애플리케이션 클라이언트 아이디 값
    private NMapController mapController;
    private NMapViewerResourceProvider mapViewerResourceProvider;
    private NMapOverlayManager mapOverlayManager;
    private NMapLocationManager mapLocationManager;
    private NMapCompassManager mapCompassManager;
    private NMapMyLocationOverlay mapMyLocationOverlay;

    private CustomDialog customDialog;

    private boolean newPointer = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_map, container, false);

        mapView = (NMapView) v.findViewById(R.id.map_view);
        mapView.setNcpClientId(CLIENT_ID);
        mapView.setClickable(true);

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        context = this.getContext();
        mMapContext = new NMapContext(super.getContext());
        mMapContext.onCreate();
        mapView.setBuiltInZoomControls(true, null);
        mapView.setOnMapStateChangeListener(OnMapViewStateChangeListener);
        mapView.setOnMapViewTouchEventListener(OnMapViewTouchEventListener);
        mapController = mapView.getMapController();
        mapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());
        mapOverlayManager = new NMapOverlayManager(context, mapView, mapViewerResourceProvider);
        mapLocationManager = new NMapLocationManager(context);
        mapLocationManager.setOnLocationChangeListener(onMyLocationChangeListener);
        // compass manager
        mapCompassManager = new NMapCompassManager(this.getActivity());

        // create my location overlay
        mapMyLocationOverlay = mapOverlayManager.createMyLocationOverlay(mapLocationManager, mapCompassManager);

        startMyLocation();
        makePoiData();
    }

    private void startMyLocation() {
        Log.d("내위치", "1");
        if (mapMyLocationOverlay != null) {
            Log.d("내위치", "2");
            if (!mapOverlayManager.hasOverlay(mapMyLocationOverlay)) {
                mapOverlayManager.addOverlay(mapMyLocationOverlay);
                Log.d("내위치", "3");
            }

            if (mapLocationManager.isMyLocationEnabled()) {
                Log.d("내위치", "4");
                if (!mapView.isAutoRotateEnabled()) {
                    mapMyLocationOverlay.setCompassHeadingVisible(true);
                    mapCompassManager.enableCompass();
                    mapView.setAutoRotateEnabled(true, false);
                    Log.d("내위치", "5");
                } else {
                    Log.d("내위치", "6");
                    stopMyLocation();
                }
                mapView.postInvalidate();
            } else {
                Log.d("내위치", "7");
                boolean isMyLocationEnabled = mapLocationManager.enableMyLocation(true);
                if (!isMyLocationEnabled) {
                    Log.d("내위치", "8");
                    Toast.makeText(context, "Please enable a My Location source in system settings",
                            Toast.LENGTH_LONG).show();

                    Intent goToSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(goToSettings);
                    return;
                }
            }
        }
    }

    private void stopMyLocation() {
        if (mapMyLocationOverlay != null) {
            mapLocationManager.disableMyLocation();

            if (mapView.isAutoRotateEnabled()) {
                mapMyLocationOverlay.setCompassHeadingVisible(false);

                mapCompassManager.disableCompass();

                mapView.setAutoRotateEnabled(false, false);

                //mapContainerView.requestLayout();
            }
        }
    }



    private void makePoiData() {
        //NGeoPoint currentPoint = new NGeoPoint(127.0630205, 37.5091300);
        //mapController.setMapCenter(currentPoint);

        NMapPOIdata poiData = new NMapPOIdata(2, mapViewerResourceProvider);
        poiData.addPOIitem(127.0630205, 37.5091300, "현재 위치", NMapPOIflagType.CAT, 1);
        poiData.addPOIitem(127.061, 37.51, "도착 위치", NMapPOIflagType.CAT, 2);
        poiData.endPOIdata();


        NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);

        poiDataOverlay.showAllItems();

        poiDataOverlay.setOnStateChangeListener(onPOIdataStateChangeListener);
    }

    /* POI data State Change Listener*/
    private final NMapPOIdataOverlay.OnStateChangeListener onPOIdataStateChangeListener = new NMapPOIdataOverlay.OnStateChangeListener() {

        @Override
        public void onCalloutClick(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            Toast.makeText(context, Integer.toString(item.getId()), Toast.LENGTH_SHORT).show();
            customDialog = new CustomDialog(context,
                    "날짜가 나오고",
                    "여기도 날짜",
                    "요거는 상태",
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Coraz%C3%B3n.svg/1200px-Coraz%C3%B3n.svg.png",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                        }
                    });
            customDialog.setCancelable(true);
            customDialog.show();


        }

        @Override
        public void onFocusChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {

        }
    };

    NMapView.OnMapViewTouchEventListener OnMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {
        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {
            if (newPointer) {
                int marker1 = NMapPOIflagType.PIN;
                Log.e("터치", Double.toString(motionEvent.getX()) + " / " + Double.toString(motionEvent.getY()));
                // set POI data
                NMapPOIdata poiData = new NMapPOIdata(1, mapViewerResourceProvider);

                poiData.beginPOIdata(1);
                NMapPOIitem item = poiData.addPOIitem(null, "Touch & Drag to Move", marker1, 0);

                // initialize location to the center of the map view.
                NGeoPoint point = mapView.getMapProjection().fromPixels((int) motionEvent.getX(), (int) motionEvent.getY());

                item.setPoint(point);

                // set floating mode
                item.setFloatingMode(NMapPOIitem.FLOATING_TOUCH | NMapPOIitem.FLOATING_DRAG);

                // show right button on callout
                item.setRightButton(true);

                poiData.endPOIdata();

                // create POI data overlay
                NMapPOIdataOverlay poiDataOverlay = mapOverlayManager.createPOIdataOverlay(poiData, null);

                poiDataOverlay.setOnFloatingItemChangeListener(onPOIdataFloatingItemChangeListener);
                newPointer = false;
            }
        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {

        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {

        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {

        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {

        }
    };

    private final NMapPOIdataOverlay.OnFloatingItemChangeListener onPOIdataFloatingItemChangeListener = new NMapPOIdataOverlay.OnFloatingItemChangeListener() {

        @SuppressLint("LongLogTag")
        @Override
        public void onPointChanged(NMapPOIdataOverlay poiDataOverlay, NMapPOIitem item) {
            NGeoPoint point = item.getPoint();


            Log.d("onPOIdataFloatingItemChangeListener", "onPointChanged: point=" + point.toString());

            //findPlacemarkAtLocation(point.longitude, point.latitude);

            item.setTitle("여기가 맞나요?");
            Toast.makeText(context, "등록하러가기 프로세스?", Toast.LENGTH_SHORT).show();


        }
    };

    NMapView.OnMapStateChangeListener OnMapViewStateChangeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {


            if (nMapError == null) {

                if (mapLocationManager.isMyLocationEnabled()) {
                    //지도 내위치로 초기화 함
                    mapController.setMapCenter(new NGeoPoint(mapLocationManager.getMyLocation().getLatitude(), mapLocationManager.getMyLocation().getLongitude()));
                    Log.d("위도 경도" + mapLocationManager.getMyLocation().getLatitude(), "dd" + mapLocationManager.getMyLocation().getLongitude());
                } else {

                }
            } else {
                Log.d("초기화 에러에러에러에러", "errrrr");

            }
        }//맵 초기화

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

        }

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {

        }

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {

        }

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

        }
    };

    /* MyLocation Listener */
    private final NMapLocationManager.OnLocationChangeListener onMyLocationChangeListener = new NMapLocationManager.OnLocationChangeListener() {

        @Override
        public boolean onLocationChanged(NMapLocationManager locationManager, NGeoPoint myLocation) {

            if (mapController != null) {
                mapController.animateTo(myLocation);
            }

            return true;
        }

        @Override
        public void onLocationUpdateTimeout(NMapLocationManager locationManager) {

            // stop location updating
            //			Runnable runnable = new Runnable() {
            //				public void run() {
            //					stopMyLocation();
            //				}
            //			};
            //			runnable.run();

            Toast.makeText(context, "Your current location is temporarily unavailable.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onLocationUnavailableArea(NMapLocationManager locationManager, NGeoPoint myLocation) {

            Toast.makeText(context, "Your current location is unavailable area.", Toast.LENGTH_LONG).show();

            stopMyLocation();
        }

    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
        if (nMapError == null) {
            startMyLocation();
            //makePoiData();
        } else {
            Log.e("map init error", nMapError.message);
        }
    }

    @Override
    public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {

    }

    @Override
    public void onMapCenterChangeFine(NMapView nMapView) {

    }

    @Override
    public void onZoomLevelChange(NMapView nMapView, int i) {

    }

    @Override
    public void onAnimationStateChange(NMapView nMapView, int i, int i1) {

    }

    @Override
    public void onFocusChanged(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }

    @Override
    public void onCalloutClick(NMapPOIdataOverlay nMapPOIdataOverlay, NMapPOIitem nMapPOIitem) {

    }
}