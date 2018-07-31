package com.seoul.tnr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.seoul.cms.helper.CustomDialog;
import com.seoul.cms.helper.LoadingDialogHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;




public class ListOrderFragment extends Fragment {

    GridView catGv;

    private Context context;

    //이미지 배열 선언
    ArrayList<String> catArr = new ArrayList<String>();

    String myJSON;

    private static final String TAG_IMG = "dong";

    private CustomDialog customDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        String url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTPZ5N0jd105TlA2z3oYXfzUn55HVlTDjqM9gMY0TRuBVvvgGxQ";
        for (int i = 0 ; i < 10 ; i++) {
            catArr.add("https://upload.wikimedia.org/wikipedia/commons/thumb/4/44/Coraz%C3%B3n.svg/1200px-Coraz%C3%B3n.svg.png");
        }
        GridAdapter gridAdapter = new GridAdapter(this.getContext(), catArr);

        GridView catGv = view.findViewById(R.id.catGv);

        catGv.setAdapter(gridAdapter);

        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        context = this.getContext();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onStop() {

        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    private class GetData extends AsyncTask<String, Void, String> {
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LoadingDialogHelper.getInstance(context).show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            LoadingDialogHelper.getInstance(context).hide();

            if (result != null) {
                myJSON = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            //String searchKeyword = params[0];
            String serverURL = "http://49.236.136.62/address/" + URLEncoder.encode("all");

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
                String id = item.getString(TAG_IMG);

                catArr.add(id);
                Log.d(TAG_IMG, id);
                HashMap<String,String> hashMap = new HashMap<>();

                hashMap.put(TAG_IMG, id);
            }
            GridAdapter gridAdapter = new GridAdapter(context, catArr);
            catGv.setAdapter(gridAdapter);


        } catch (JSONException e) {
            Log.d("JSONException", "showResult : ", e);
        }
    }


    public class GridAdapter extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<String> carArr;

        public GridAdapter(Context context, ArrayList<String> carArr){
            this.context = context;
            this.carArr = carArr;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return carArr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return carArr.get(position);
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
                convertView = inflater.inflate(R.layout.cat_list_item, parent, false);
            }


            ImageView catIv = convertView.findViewById(R.id.catIv);
            LinearLayout catlistItemLl = convertView.findViewById(R.id.catlistItemLl);

            Glide.with(convertView).load(carArr.get(position)).into(catIv);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) catlistItemLl.getLayoutParams();

            if (position%3==0){
                layoutParams.leftMargin = 20;
            } else if (position%3 == 2) {
                layoutParams.rightMargin = 20;
            }
            catlistItemLl.setLayoutParams(layoutParams);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
            });
            return convertView;
        }

    }

    /*private void getData() {
        String like = "";
        for(int i=0; i<10; i++) {
            if (i%2==0)
                like = "0";
            else
                like = "1";
            addList("1", Integer.toString(i), "1", like);
        }
    }

    public void addList(final String cat_img, String title, String content, String like) {
        View view = null;

        view = getLayoutInflater().inflate(R.layout.list_item, null);

        TextView listItemTv1 = (TextView) view.findViewById(R.id.listItemTv1);


        ImageView catIv = (ImageView) view.findViewById(R.id.catIv);
        ImageView likeIv = (ImageView) view.findViewById(R.id.likeIv);

        String IO = "";
        String TF = "";
        listItemTv1.setText(title);
        if (like.equals("1")) { // 즐겨찾기 표시
            likeIv.setImageResource(R.drawable.ic_like_empty);
            IO = "- ";
            TF = "to.";
        } else { // 즐겨찾기 표시
            likeIv.setImageResource(R.drawable.ic_like_full);
            IO = "+ ";
            TF = "from.";
        }

        llContainer.addView(view);

    }*/
}
