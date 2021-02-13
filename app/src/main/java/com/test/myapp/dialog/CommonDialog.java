package com.test.myapp.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;


public class CommonDialog {

    private static AlertDialog alertDialog;
    private static final String TAG = "DVDialog -> ";

    /**
     * general image_picker_dialog.
     */
    public static void showGeneralDialog(Context context, String title, String message, String buttonTxt, OnDialogButtonsClick listener) {
        try {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle(title);
            alertDialog.setCancelable(false);
            alertDialog.setMessage(message);
            alertDialog.setButton(buttonTxt, (dialog, which) -> {
                if (listener != null) {
                    listener.onPositiveButtonClick();
                    dialog.cancel();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * general image_picker_dialog with negative positive button
     */
    public static void showNPButtonDialog(Context context, String title, String message, String pButtonTxt, String nButtonTxt, OnDialogButtonsClick listener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton(pButtonTxt, (dialog, id) -> {
                        if (listener != null)
                            listener.onPositiveButtonClick();
                    })
                    .setNegativeButton(nButtonTxt, (dialog, id) -> {
                        if (listener != null) {
                            listener.onNegativeButtonClick();
                            dialog.cancel();
                        }
                    });
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    public static void showSettingsDialog(Context context) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                ((Activity)context).startActivityForResult(intent,121);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
