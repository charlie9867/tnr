package com.seoul.tnr;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListOrderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class ListOrderFragment extends Fragment {

    LinearLayout llContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        llContainer = (LinearLayout) view.findViewById(R.id.llContainer);
        getData();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
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

    private void getData() {
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

    }
}
