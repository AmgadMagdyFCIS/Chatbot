package com.example.freshman_guide_chatbot.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.example.freshman_guide_chatbot.R;

public class dialog {
    private Activity activity;
    private AlertDialog dialog;

    dialog(Activity myActivity) {
        activity = myActivity;
    }
    @SuppressLint("InflateParams")
    void startLoadingdialog() {

        // adding ALERT Dialog builder object and passing activity as parameter
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // layoutinflater object and use activity to get layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading, null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    // dismiss method
    void dismissdialog() {
        dialog.dismiss();
    }
}
