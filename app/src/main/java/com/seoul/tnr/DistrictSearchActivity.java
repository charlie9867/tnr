package com.seoul.tnr;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.seoul.cms.base.BaseActivity;
import com.seoul.cms.base.BaseResponse;
import com.seoul.cms.helper.LoadingDialogHelper;
import com.seoul.cms.model.AddressInfo;
import com.seoul.cms.model.AddressList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DistrictSearchActivity extends BaseActivity {
    @BindView(R.id.districtListGv)
    GridView districtListGv;


    Activity activity = this;

    //이미지 배열 선언
    ArrayList<String> districtArr = new ArrayList<String>();

    String myJSON;

    private static final String TAG_NAME = "juso";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_search);
        super.setActionBarVisible(true);
        ButterKnife.bind(this);

/*        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPZ5N0jd105TlA2z3oYXfzUn55HVlTDjqM9gMY0TRuBVvvgGxQ";
        for (int i = 0 ; i < 10 ; i++) {
            districtArr.add(Integer.toString(i));
        }
        GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), districtArr);

        districtListGv.setAdapter(gridAdapter);*/


        GetData task = new GetData();

        task.execute();
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

    private class GetData extends AsyncTask<String, Void, String>{
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingDialogHelper.getInstance(DistrictSearchActivity.this).show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LoadingDialogHelper.getInstance(DistrictSearchActivity.this).hide();
            if (result != null) {
                myJSON = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //String searchKeyword = params[0];
            String serverURL = "http://106.10.45.41";
            serverURL = "http://49.236.136.62/address/district";
            //String postParameters = "country=" + searchKeyword;
            try {
                URL url = new URL(serverURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                /*OutputStream outputStream = httpURLConnection.getOutputStream();
                //outputStream.write(postParameters.getBytes("UTF-8"));

                outputStream.flush();
                outputStream.close();*/

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d("rescode", "response code - " + responseStatusCode);
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                Log.d("StringBuilder", "string - " + sb.toString());
                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                errorString = e.toString();
                Log.d("Exception Error", errorString);
                return null;
            }
        }
    }

    private void showResult(){

        try {

            JSONObject jsonObject = new JSONObject(myJSON);

            JSONArray jsonArray = jsonObject.getJSONArray("district_list");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String id = item.getString(TAG_NAME);

                districtArr.add(id);
                Log.d(TAG_NAME, id);
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, id);
            }

            GridAdapter gridAdapter = new GridAdapter(getApplicationContext(), districtArr);
            districtListGv.setAdapter(gridAdapter);


        } catch (JSONException e) {
            Log.d("JSONException", "showResult : ", e);
        }
    }


    public class GridAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> districtArr;

        public GridAdapter(Context context, ArrayList<String> districtArr){
            this.context = context;
            this.districtArr = districtArr;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return districtArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return districtArr.get(position);
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
            LinearLayout listItemLl = convertView.findViewById(R.id.listItemLl);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listItemLl.getLayoutParams();

            districtTv.setText(districtArr.get(position));

            if (position%2==0){
                layoutParams.leftMargin = 20;
            } else {
                layoutParams.rightMargin = 20;
            }
            listItemLl.setLayoutParams(layoutParams);

            /*ImageView imageView = convertView.findViewById(R.id.districtIv);

            Glide.with(context).load(districtArr.get(position)).into(imageView);


            imageView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                }

            });*/
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, StreetSearchActivity.class);
                    intent.putExtra("DISTRICT", districtArr.get(position));
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }

}