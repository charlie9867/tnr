package com.seoul.cms.helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.seoul.tnr.R;


/**
 * Created by byapps on 2016. 2. 5..
 */
public class CustomDialog extends Dialog {
    private Context context;
    private TextView dialogApplyDateTv;
    private TextView dialogOrderDateTv;
    private TextView dialogStatusTv;
    private ImageView catIv;
    private ImageView likeIv;
    private String catImageUrl;
    private String applyDate;
    private String orderDate;
    private String status;
    private View.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.3f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.custom_dialog);
        setLayout();

        setContent(applyDate, orderDate, status, onClickListener);
    }
    private void setLayout(){
        dialogApplyDateTv = (TextView) findViewById(R.id.dialogApplyDateTv);
        dialogOrderDateTv = (TextView) findViewById(R.id.dialogOrderDateTv);
        dialogStatusTv = (TextView) findViewById(R.id.dialogStatusTv);
        catIv = (ImageView) findViewById(R.id.catIv);
        likeIv = (ImageView) findViewById(R.id.likeIv);
    }
    public CustomDialog(Context context) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
    }

    public CustomDialog(Context context, String applyDate, String orderDate, String  status, String imgUrl,
                        View.OnClickListener onClickListener) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.applyDate = applyDate;
        this.orderDate = orderDate;
        this.status = status;
        this.catImageUrl = imgUrl;
        this.onClickListener = onClickListener;
    }

    private void setContent(String applyDate, String orderDate, String status, View.OnClickListener onClickListener){

        dialogApplyDateTv.setText(applyDate);
        dialogOrderDateTv.setText(orderDate);
        dialogStatusTv.setText(status);
        likeIv.setOnClickListener(onClickListener);

        if(catImageUrl.equals("")){
            this.catIv.setVisibility(View.GONE);
        }else{
            Glide.with(context).load(catImageUrl).into(this.catIv);

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){

        if(keyCode== KeyEvent.KEYCODE_BACK){
            this.dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }



    /*@Override
    public boolean dipatchTouchEvent(MotionEvent ev) {
        Rect dialogBounds = new Rect();
        getWindow().getDecorView().getHitRect(dialogBounds);

        if(!dialogBounds.contains((int) ev.getX(), (int) ev.getY())){
            this.dismiss();

        }
        return super.dispatchTouchEvent(ev);
    }*/

}
