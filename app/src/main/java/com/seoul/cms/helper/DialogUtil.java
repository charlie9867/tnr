package com.seoul.cms.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.seoul.tnr.R;


public class DialogUtil {

    public static void alert(Context context, String message) {
        alert(context, R.mipmap.ic_launcher, "알림", message);
    }

    public static void alert(Context context, String title, String message) {
        alert(context, 0, title, message);
    }

    public static void alert(Context context, int icon, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if ( icon > 0 ) builder.setIcon(icon);
        if ( title != null ) builder.setTitle(title);
        if ( message != null ) builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton("확인", null);

        builder.create();
        builder.show();
    }

    public static void showConfirmDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("확인");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("What ???");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "확인 눌러짐", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "취소 눌러짐", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "취소됨", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();
        builder.show();
    }

    public static void showListDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("알림");
        builder.setIcon(R.mipmap.ic_launcher);

        final String[] list = {"축구", "농구", "배구"};

        builder.setItems(list, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, list[which], Toast.LENGTH_SHORT).show();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "취소됨", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create();
        builder.show();
    }
    
}
