package com.seoul.tnr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.seoul.cms.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StreetSearchActivity extends BaseActivity {

    @BindView(R.id.streetListGv)
    GridView streetListGv;

    @BindView(R.id.searchMenuTv)
    TextView searchMenuTv;

    Activity activity = this;

    //이미지 배열 선언
    ArrayList<String> imgArr = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_search);
        ButterKnife.bind(this);
        super.setActionBarVisible(true);

        searchMenuTv.setText(R.string.searchMenuStreetEx);

        for (int i = 0 ; i < 10 ; i++) {
            imgArr.add(Integer.toString(i));
        }
        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), imgArr);

        streetListGv.setAdapter(gridAdapter);
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
