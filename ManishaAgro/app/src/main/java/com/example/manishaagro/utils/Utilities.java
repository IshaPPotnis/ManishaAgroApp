package com.example.manishaagro.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.manishaagro.R;
import com.example.manishaagro.employee.CustomerVisitStartActivity;

public class Utilities {

    public static void showAlertDialog(Context activityClass, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activityClass);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
