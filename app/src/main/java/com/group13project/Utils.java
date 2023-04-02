package com.group13project;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    /**
     * display a toast message
     *
     * @param context context for the `Toast` object
     * @param msg     the message which should be displayed
     */
    public static void toast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
