package com.seoul.tnr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seoul.cms.base.BaseActivity;
import com.seoul.cms.helper.LoadingDialogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StreetSearchActivity extends BaseActivity {

    @BindView(R.id.streetListGv)
    GridView streetListGv;

    @BindView(R.id.searchMenuTv)
    TextView searchMenuTv;

    Activity activity = this;

    //이미지 배열 선언
    ArrayList<String> streetArr = new ArrayList<String>();


    String myJSON, district;


    private static final String TAG_STREET = "dong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_search);
        ButterKnife.bind(this);
        super.setActionBarVisible(true);
        Intent intent = getIntent();
        district = intent.getExtras().getString("DISTRICT");
        if (district.equals("")){
            Toast.makeText(activity, "정보를 가져오지 못했습니다.", Toast.LENGTH_SHORT).show();
            activity.finish();
        }
        searchMenuTv.setText(R.string.searchMenuStreetEx);

        GetData task = new GetData();

        task.execute();
    }


    private class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingDialogHelper.getInstance(StreetSearchActivity.this).show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LoadingDialogHelper.getInstance(StreetSearchActivity.this).hide();

            if (result != null) {
                myJSON = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //String searchKeyword = params[0];
            Log.d("district", district);
            String serverURL = "http://49.236.136.62/address/" + URLEncoder.encode(district);

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

            JSONArray jsonArray = jsonObject.getJSONArray("street_list");

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);
                String id = item.getString(TAG_STREET);

                streetArr.add(id);
                Log.d(TAG_STREET, id);
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_STREET, id);
            }

            StreetSearchActivity.GridAdapter gridAdapter = new StreetSearchActivity.GridAdapter(getApplicationContext(), streetArr);
            streetListGv.setAdapter(gridAdapter);


        } catch (JSONException e) {
            Log.d("JSONException", "showResult : ", e);
        }
    }


    public class GridAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> streetArr;

        public GridAdapter(Context context, ArrayList<String> streetArr){
            this.context = context;
            this.streetArr = streetArr;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return streetArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return streetArr.get(position);
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

            districtTv.setText(streetArr.get(position));

            if (position%3==0){
                layoutParams.leftMargin = 20;
            } else if (position%3 == 2) {
                layoutParams.rightMargin = 20;
            }
            listItemLl.setLayoutParams(layoutParams);

            /*ImageView imageView = convertView.findViewById(R.id.districtIv);

            Glide.with(context).load(streetArr.get(position)).into(imageView);


            imageView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                }

            });*/
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ImageAddActivity.class);
                    intent.putExtra("DISTRICT", district);
                    intent.putExtra("STREET", streetArr.get(position));
                    startActivity(intent);
                }
            });
            return convertView;
        }

    }
}