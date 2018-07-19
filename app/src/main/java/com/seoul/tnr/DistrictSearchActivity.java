package com.seoul.tnr;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


import com.seoul.cms.base.BaseActivity;
import com.seoul.cms.base.BaseResponse;
import com.seoul.cms.helper.LogHelper;
import com.seoul.cms.model.AddressInfo;
import com.seoul.cms.model.AddressList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DistrictSearchActivity extends BaseActivity {
    @BindView(R.id.districtListGv)
    GridView districtListGv;


    Activity activity = this;

    //이미지 배열 선언
    ArrayList<String> imgArr = new ArrayList<String>();

    private ArrayList<AddressInfo> mItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_search);
        super.setActionBarVisible(true);
        ButterKnife.bind(this);

        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPZ5N0jd105TlA2z3oYXfzUn55HVlTDjqM9gMY0TRuBVvvgGxQ";
        for (int i = 0 ; i < 10 ; i++) {
            imgArr.add(Integer.toString(i));
        }
        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), imgArr);

        districtListGv.setAdapter(gridAdapter);
    }

    public void getAddressList() {
        // 데이터 목록
        client.init(R.string.address_list, "district");

        client.post(new BaseResponse<AddressList>(DistrictSearchActivity.this) {
            @Override
            public void onResponse(AddressList response) {




            }
        });
    }




    public class GridAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> imgArr;

        public GridAdapter(Context context, ArrayList<String> imgArr){
            this.context = context;
            this.imgArr = imgArr;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imgArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return imgArr.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // TODO Auto-generated method stub
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.district_list_item, parent, false);
            }


            TextView districtTv = convertView.findViewById(R.id.districtTv);

            districtTv.setText(imgArr.get(position));

            /*ImageView imageView = convertView.findViewById(R.id.districtIv);

            Glide.with(context).load(imgArr.get(position)).into(imageView);


            imageView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                }

            });*/
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, StreetSearchActivity.class);
                    startActivity(intent);

                }
            });
            return convertView;
        }

    }

}
